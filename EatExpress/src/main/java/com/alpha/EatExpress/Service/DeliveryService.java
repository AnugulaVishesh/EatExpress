package com.alpha.EatExpress.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.DeliveryPartnerRegDto;
import com.alpha.EatExpress.Entity.Deliverypartner;
import com.alpha.EatExpress.Repository.CustomerRepository;
import com.alpha.EatExpress.Repository.DeliveryRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryrepo;

   
    // Register delivery partner
    public String registerDeliveryPartner(DeliveryPartnerRegDto dto){

       
        Deliverypartner dp = new Deliverypartner();
        dp.setName(dto.getName());
        dp.setMobno(dto.getMobno());
        dp.setEmailid(dto.getEmailid());
        dp.setVechicalNo(dto.getVechicalNo());

        deliveryrepo.save(dp);
        

        return "Delivery Partner Registered Successfully";
    }


	


	public void deleteDelivery(Long mob) {
		// TODO Auto-generated method stub
		Deliverypartner d= deliveryrepo.findBymobno(mob);
		deliveryrepo.delete(d);
	}


	
	     
	     
		
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  


