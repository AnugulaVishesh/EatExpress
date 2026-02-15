package com.alpha.EatExpress.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.RestaurantRegDto;
import com.alpha.EatExpress.Entity.ResponseStructure;
import com.alpha.EatExpress.Entity.Restaurant;
import com.alpha.EatExpress.Exception.RestaurantNotFound;
import com.alpha.EatExpress.Repository.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public String registerRestaurant(RestaurantRegDto dto){

        Restaurant r = new Restaurant();
        r.setName(dto.getName());
        r.setMobno(dto.getMobno());
        r.setMailid(dto.getMailid());
        r.setAddress(dto.getAddress());
        r.setDescription(dto.getDescription());
        r.setPackagingfee(dto.getPackagingfee());
        r.setType(dto.getType());
        r.setStatus("OPEN");
        r.setRating("0");

        restaurantRepository.save(r);

        return "Restaurant Registered Successfully";
    }
    
    
	public void deleterestaurant(Long mob) {
		restaurantRepository.deleteByMobno(mob);
	}


	public ResponseStructure<Restaurant> findrestaurant(Long mob) {
		ResponseStructure<Restaurant> rs= new ResponseStructure<Restaurant>();
		
		Restaurant r=restaurantRepository.findBymobno(mob).orElseThrow(() -> new RestaurantNotFound() );
		 rs.setStatuscode(200);
		    rs.setMessage("Restaurant found successfully");
		    rs.setData(r);

		    return rs;
		
	}
    
    
    
}
