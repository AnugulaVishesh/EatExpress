package com.alpha.EatExpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.RestaurantDTO;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;

import jakarta.validation.Valid;

import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Service.RestaurantService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<ResponceStructure<Restaurant>> register(@RequestBody @Valid RestaurantDTO rdto){
        return restaurantService.register(rdto);
    }

    @GetMapping("/find")
    public ResponseEntity<ResponceStructure<Restaurant>> find(@RequestParam long mobno){
        return restaurantService.findRestaurant(mobno);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponceStructure<String>> delete(@RequestParam long mobno){
        return restaurantService.deleteRestaurant(mobno);
    }

    @PostMapping("/additemtomenu")
    public ResponseEntity<ResponceStructure<Restaurant>> addItem(
            @RequestParam long mobno,
            @RequestBody Item item){
        return restaurantService.addItemToMenu(mobno,item);
    }

    @PatchMapping("/updatestatus")
    public ResponseEntity<ResponceStructure<String>> updateStatus(
            @RequestParam long mobno,
            @RequestParam String status){
        return restaurantService.updateStatus(mobno,status);
    }

    @PatchMapping("/updateitemavailability")
    public ResponseEntity<ResponceStructure<String>> updateItemAvailability(
            @RequestParam int itemid,
            @RequestParam String availability){
        return restaurantService.updateItemAvailability(itemid,availability);
    }

    @GetMapping("/findnearbypartners")
    public ResponseEntity<ResponceStructure<List<String>>> findNearbyPartners(
            @RequestParam double latitude,
            @RequestParam double longitude){
        return restaurantService.findNearbyDeliveryPartners(latitude,longitude);
    }

    @PostMapping("/acceptorder")
    public ResponseEntity<ResponceStructure<List<String>>> acceptOrder(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam Integer orderid){
        return restaurantService.acceptOrder(latitude,longitude,orderid);
    }

    @GetMapping("/getMenu")
    public ResponseEntity<ResponceStructure<List<Item>>> getMenu(@RequestParam long mobno){
        return restaurantService.getMenu(mobno);
    }

    @PatchMapping("/updateItemDetails")
    public ResponseEntity<ResponceStructure<Item>> updateItemDetails(
            @RequestParam long mobno,
            @RequestParam int itemid,
            @RequestBody Item item){
        return restaurantService.updateItemDetails(mobno,itemid,item);
    }

    @DeleteMapping("/removeitemfrommenu")
    public ResponseEntity<ResponceStructure<String>> removeItemFromMenu(
            @RequestParam long mobno,
            @RequestParam int itemid){
        return restaurantService.removeItemFromMenu(mobno,itemid);
    }
    
    @PutMapping("/cancelOrder")
    public ResponseEntity<ResponceStructure<Order>> cancelOrder(
            @RequestParam long restaurantMobNo,
            @RequestParam int orderId) {

        return restaurantService.cancelOrder(restaurantMobNo, orderId);
    }
    
    @PostMapping("/x")
    public void registerRestaurant(@RequestBody @Valid RestaurantDTO restaurantDTO) {

        restaurantService.registerRestaurant(restaurantDTO);

    }
    
    
    
}