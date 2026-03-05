package com.alpha.EatExpress.Servicee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.EatExpress.DTO.RestaurantDTO;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.Address;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;
import com.alpha.EatExpress.repository.OrderRepo;
import com.alpha.EatExpress.repository.RestaurantRepo;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo restaurantrepo;

    @Autowired
    private RestTemplate resttemplate;
    
    @Autowired
    private OrderRepo orderRepo;

    public ResponseEntity<ResponceStructure<Restaurant>> save(RestaurantDTO rdto) {

    	Restaurant r = new Restaurant();

    	r.setName(rdto.getName());
    	r.setMobno(rdto.getMobno());
    	r.setMail(rdto.getMail());
    	r.setDescription(rdto.getDescription());
    	r.setPackagingFee(rdto.getPackagingFee());
    	r.setType(rdto.getType());

    	r.setStatus("OPEN");
    	r.setRatings(0);

        String url = "https://us1.locationiq.com/v1/reverse?key=pk.7de45e6b5eecd2b0cac58b13e3b3012e&lat="
                + rdto.getCoordinates().getLatitude()
                + "&lon=" + rdto.getCoordinates().getLongitude()
                + "&format=json";

        Map<String, Object> response = resttemplate.getForObject(url, Map.class);

        Map<String, Object> addressMap = (Map<String, Object>) response.get("address");

        Address address = new Address();

        address.setLatitude(rdto.getCoordinates().getLatitude());
        address.setLongitude(rdto.getCoordinates().getLongitude());
        address.setArea((String) addressMap.get("suburb"));
        address.setCity((String) addressMap.get("city"));
        address.setDistrict((String) addressMap.get("county"));
        address.setState((String) addressMap.get("state"));
        address.setCountry((String) addressMap.get("country"));

        if (addressMap.get("postcode") != null) {
            address.setPincode(Integer.parseInt((String) addressMap.get("postcode")));
        }

        r.setAddress(address);

        restaurantrepo.save(r);

        ResponceStructure<Restaurant> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Restaurant saved successfully");
        rs.setData(r);

        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponceStructure<Restaurant>> addItemToMenu(long restaurantmobno, Item item) {

        Restaurant restaurant = restaurantrepo.findByMobno(restaurantmobno)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        item.setRestaurant(restaurant);

        if (restaurant.getMenuItems() == null) {
            restaurant.setMenuItems(new ArrayList<>());
        }

        restaurant.getMenuItems().add(item);

        restaurantrepo.save(restaurant);

        ResponceStructure<Restaurant> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item added successfully");
        rs.setData(restaurant);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<Restaurant>> findrestaurant(long mobno) {

        Restaurant restaurant = restaurantrepo.findByMobno(mobno)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        ResponceStructure<Restaurant> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Restaurant fetched successfully");
        rs.setData(restaurant);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<Restaurant>> deleteRestaurant(long mobno) {

        Restaurant restaurant = restaurantrepo.findByMobno(mobno)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurantrepo.delete(restaurant);

        ResponceStructure<Restaurant> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Restaurant deleted successfully");
        rs.setData(null);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
    
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
   
    public List<String> acceptorder(double latitude, double longitude, Integer orderid) {
        Order order= orderRepo.findById(orderid).orElseThrow(()->new RuntimeException("Order does not exist"));
      List<String> nearbyPartners= redisService.findNearbyPartners(latitude,longitude,5.0);
      String orderKey= "order:"+orderid;
      for(String partnerid:nearbyPartners){
           redisTemplate.opsForSet().add(orderKey, partnerid);

      }
       return nearbyPartners;
 }

    public ResponseEntity<ResponceStructure<String>> updateItemAvailability(
            long restaurantmobno, int itemid, String availability) {

        Restaurant restaurant = restaurantrepo.findByMobno(restaurantmobno)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Item item = restaurant.getMenuItems().stream()
                .filter(i -> i.getId() == itemid)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setAvailability(availability);

        restaurantrepo.save(restaurant);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item availability updated successfully");
        rs.setData("Item ID: " + itemid + " updated");

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> updateRestaurantAvailability(
            long mobno, String availability) {

        Restaurant restaurant = restaurantrepo.findByMobno(mobno)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setStatus(availability);
        restaurantrepo.save(restaurant);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Restaurant availability updated");
        rs.setData(availability);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}