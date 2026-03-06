package com.alpha.EatExpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.RestaurantDTO;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Servicee.RestaurantService;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Restaurant;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantservice;

    @PostMapping("/register")
    public ResponseEntity<ResponceStructure<Restaurant>> saveRestaurant(@RequestBody RestaurantDTO rdto) {
        return restaurantservice.save(rdto);
    }

    @GetMapping("/findrestaurant")
    public ResponseEntity<ResponceStructure<Restaurant>> findrestaurant(@RequestParam long mobno) {
        return restaurantservice.findrestaurant(mobno);
    }

    @DeleteMapping("/deleterestaurant")
    public ResponseEntity<ResponceStructure<Restaurant>> deleteRestaurant(@RequestParam long mobno) {
        return restaurantservice.deleteRestaurant(mobno);
    }

    @PatchMapping("/additemtomenu")
    public ResponseEntity<ResponceStructure<Restaurant>> addItemToMenu(
            @RequestParam long restaurantmobno,
            @RequestBody Item item) {

        return restaurantservice.addItemToMenu(restaurantmobno, item);
    }

    @PatchMapping("/updateitemavailability")
    public ResponseEntity<ResponceStructure<String>> updateItemAvailability(
            @RequestParam long restaurantmobno,
            @RequestParam int itemid,
            @RequestParam String availability) {

        return restaurantservice.updateItemAvailability(restaurantmobno, itemid, availability);
    }
    
    @PostMapping("/acceptorder")
    public List<String> acceptorder(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam Integer orderid){

        return restaurantservice.acceptorder(latitude, longitude, orderid);
    }

    @PatchMapping("/updaterestaurantavailability")
    public ResponseEntity<ResponceStructure<String>> updateRestaurantAvailability(
            @RequestParam long mobno,
            @RequestParam String availability) {

        return restaurantservice.updateRestaurantAvailability(mobno, availability);
    }
    
   
}