package com.alpha.EatExpress.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.RestaurantRegDto;
import com.alpha.EatExpress.Entity.ResponseStructure;
import com.alpha.EatExpress.Entity.Restaurant;
import com.alpha.EatExpress.Service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    
    @PostMapping("/register")
    public String registerRestaurant(@RequestBody RestaurantRegDto dto){
        return restaurantService.registerRestaurant(dto);
    }
    
    @GetMapping("/findRestaurant")
    public ResponseStructure<Restaurant> findRestaurant(@RequestParam Long mob) {
    	
    return	restaurantService.findrestaurant(mob);
    }
    
    
    @DeleteMapping()
    public void deleterestaurant(@RequestParam Long mob) {
    	restaurantService.deleterestaurant(mob);
    }
    
    

}
