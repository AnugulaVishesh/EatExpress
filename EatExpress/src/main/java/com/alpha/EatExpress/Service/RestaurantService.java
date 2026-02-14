package com.alpha.EatExpress.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.RestaurantRegDto;
import com.alpha.EatExpress.Entity.Restaurant;
import com.alpha.EatExpress.Repository.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public String registerRestaurant(RestaurantRegDto dto){

        // check already exists
        if(restaurantRepository.existsByMailid(dto.getMailid())){
            return "Restaurant already registered with this email";
        }

        Restaurant r = new Restaurant();
        r.setName(dto.getName());
        r.setMobno(dto.getMobno());
        r.setMailid(dto.getMailid());
        r.setAddress(dto.getAddress());
        r.setDescription(dto.getDescription());
        r.setPackagingfee(dto.getPackagingfee());
        r.setType(dto.getType());

        // default values
        r.setStatus("OPEN");
        r.setRating("0");

        restaurantRepository.save(r);

        return "Restaurant Registered Successfully";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    
    
    
}
