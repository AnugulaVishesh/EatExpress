package com.alpha.EatExpress.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.RestaurantDTO;
import com.alpha.EatExpress.entity.Address;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;
import com.alpha.EatExpress.Exception.ItemNotFoundException;
import com.alpha.EatExpress.Exception.OrderNotFoundException;
import com.alpha.EatExpress.Exception.RestaurantNotFoundException;
import com.alpha.EatExpress.repository.ItemRepository;
import com.alpha.EatExpress.repository.OrderRepository;
import com.alpha.EatExpress.repository.RestaurantRepository;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    // REGISTER RESTAURANT
    public ResponseEntity<ResponceStructure<Restaurant>> register(RestaurantDTO rdto){

        Restaurant restaurant = new Restaurant();

        restaurant.setName(rdto.getName());
        restaurant.setMail(rdto.getMail());
        restaurant.setMobno(rdto.getMobno());
        restaurant.setStatus("CLOSED");
        restaurant.setDescription(rdto.getDescription());
        restaurant.setPackagingFee(rdto.getPackagingFee());
        restaurant.setType(rdto.getType());

        Address address = new Address();
        address.setLatitude(rdto.getCoordinates().getLatitude());
        address.setLongitude(rdto.getCoordinates().getLongitude());

        restaurant.setAddress(address);

        Restaurant savedRestaurant = restaurantRepo.save(restaurant);

        ResponceStructure<Restaurant> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Restaurant Registered Successfully");
        rs.setData(savedRestaurant);

        return new ResponseEntity<>(rs,HttpStatus.CREATED);
    }


    // FIND RESTAURANT
    public ResponseEntity<ResponceStructure<Restaurant>> findRestaurant(long mobno){

        Restaurant restaurant = restaurantRepo.findByMobno(mobno)
                .orElseThrow(() -> new RestaurantNotFoundException());

        ResponceStructure<Restaurant> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Restaurant Found");
        rs.setData(restaurant);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // DELETE RESTAURANT
    public ResponseEntity<ResponceStructure<String>> deleteRestaurant(long mobno){

        Restaurant restaurant = restaurantRepo.findByMobno(mobno)
                .orElseThrow(() -> new RestaurantNotFoundException());

        restaurantRepo.delete(restaurant);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Restaurant Deleted Successfully");
        rs.setData("Deleted");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // ADD ITEM TO MENU
    public ResponseEntity<ResponceStructure<Restaurant>> addItemToMenu(long mobno, Item item){

        Restaurant restaurant = restaurantRepo.findByMobno(mobno)
                .orElseThrow(() -> new RestaurantNotFoundException());

        item.setRestaurant(restaurant);

        if(restaurant.getMenuItems() == null){
            restaurant.setMenuItems(new ArrayList<>());
        }

        restaurant.getMenuItems().add(item);

        itemRepo.save(item);

        restaurantRepo.save(restaurant);

        ResponceStructure<Restaurant> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item Added To Menu");
        rs.setData(restaurant);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // UPDATE RESTAURANT STATUS
    public ResponseEntity<ResponceStructure<String>> updateStatus(long mobno,String status){

        Restaurant restaurant = restaurantRepo.findByMobno(mobno)
                .orElseThrow(() -> new RestaurantNotFoundException());

        restaurant.setStatus(status);

        restaurantRepo.save(restaurant);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Restaurant Status Updated");
        rs.setData(status);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // UPDATE ITEM AVAILABILITY
    public ResponseEntity<ResponceStructure<String>> updateItemAvailability(int itemid,String availability){

        Item item = itemRepo.findById(itemid)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        item.setAvailability(availability);

        itemRepo.save(item);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item Availability Updated");
        rs.setData(availability);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // FIND NEARBY DELIVERY PARTNERS
    public ResponseEntity<ResponceStructure<List<String>>> findNearbyDeliveryPartners(
            double latitude,double longitude){

        List<String> partners = redisService.findNearbyPartners(latitude,longitude,5.0);

        ResponceStructure<List<String>> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Nearby Delivery Partners");
        rs.setData(partners);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // ACCEPT ORDER
    public ResponseEntity<ResponceStructure<List<String>>> acceptOrder(
            double latitude,double longitude,Integer orderid){

        Order order = orderRepo.findById(orderid)
                .orElseThrow(() -> new OrderNotFoundException());

        List<String> nearbyPartners =
                redisService.findNearbyPartners(latitude,longitude,5.0);

        String orderKey = "order:"+orderid;

        for(String partnerid : nearbyPartners){
            redisTemplate.opsForSet().add(orderKey,partnerid);
        }

        ResponceStructure<List<String>> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Order sent to nearby delivery partners");
        rs.setData(nearbyPartners);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // GET MENU
    public ResponseEntity<ResponceStructure<List<Item>>> getMenu(long mobno){

        Restaurant restaurant = restaurantRepo.findByMobno(mobno)
                .orElseThrow(() ->
                        new RestaurantNotFoundException());

        List<Item> menu = restaurant.getMenuItems();

        ResponceStructure<List<Item>> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Menu fetched successfully");
        rs.setData(menu);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // UPDATE ITEM DETAILS
    public ResponseEntity<ResponceStructure<Item>> updateItemDetails(
            long mobno,
            int itemid,
            Item updatedItem){

        Restaurant restaurant = restaurantRepo.findByMobno(mobno)
                .orElseThrow(() ->
                        new RestaurantNotFoundException());

        Item item = itemRepo.findById(itemid)
                .orElseThrow(() ->
                        new ItemNotFoundException("Item not found"));

        if(!restaurant.getMenuItems().contains(item)){
            throw new RuntimeException("Item not belongs to this restaurant");
        }

        item.setName(updatedItem.getName());
        item.setDescription(updatedItem.getDescription());
        item.setPrice(updatedItem.getPrice());
        item.setAvailability(updatedItem.getAvailability());
        item.setImage(updatedItem.getImage());

        Item savedItem = itemRepo.save(item);

        ResponceStructure<Item> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item updated successfully");
        rs.setData(savedItem);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    // REMOVE ITEM FROM MENU
    public ResponseEntity<ResponceStructure<String>> removeItemFromMenu(
            long mobno,
            int itemid){

        Restaurant restaurant = restaurantRepo.findByMobno(mobno)
                .orElseThrow(() ->
                        new RestaurantNotFoundException());

        Item item = itemRepo.findById(itemid)
                .orElseThrow(() ->
                        new ItemNotFoundException("Item not found"));

        restaurant.getMenuItems().remove(item);

        restaurantRepo.save(restaurant);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item removed from menu");
        rs.setData("Removed successfully");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }


    public ResponseEntity<ResponceStructure<Order>> cancelOrder(long restaurantMobNo, int orderId) {

        Restaurant restaurant = restaurantRepo.findByMobno(restaurantMobNo).orElseThrow(() -> new RestaurantNotFoundException());

        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException());

        
        order.setStatus("CANCELLED");

        double penalty = (order.getOrderPrice().doubleValue() / 100.0) * 10;

        restaurant.setWallet(restaurant.getWallet() - penalty);

        if (restaurant.getWallet() <= -1000) {
            restaurant.setBlocked(true);
        }

        restaurantRepo.save(restaurant);
        orderRepo.save(order);

        ResponceStructure<Order> structure = new ResponceStructure<>();
        structure.setStatusCode(200);
        structure.setMessage("Order Cancelled Successfully");
        structure.setData(order);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
}