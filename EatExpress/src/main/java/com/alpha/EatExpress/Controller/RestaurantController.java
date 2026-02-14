package com.alpha.EatExpress.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.RestaurantRegDto;
import com.alpha.EatExpress.Entity.Restaurant;
import com.alpha.EatExpress.Service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    
    @PostMapping("/register")
    public String registerRestaurant(@RequestBody RestaurantRegDto dto){
        return restaurantService.registerRestaurant(dto);
    }
    
    

//    @PostMapping
//    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
//        return restaurantService.saveRestaurant(restaurant);
//    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }
}
