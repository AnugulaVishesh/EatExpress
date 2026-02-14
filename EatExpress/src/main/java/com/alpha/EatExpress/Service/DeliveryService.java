package com.alpha.EatExpress.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.DeliveryPartnerRegDto;
import com.alpha.EatExpress.Entity.Deliverypartner;
import com.alpha.EatExpress.Repository.DeliveryRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

   
    // Register delivery partner
    public String registerDeliveryPartner(DeliveryPartnerRegDto dto){

        // correct object usage
        if(deliveryRepository.existsByEmailid(dto.getEmailid())){
            return "Delivery Partner already registered with this email";
        }

        Deliverypartner dp = new Deliverypartner();
        dp.setName(dto.getName());
        dp.setMobno(dto.getMobno());
        dp.setEmailid(dto.getEmailid());
        dp.setVechicalNo(dto.getVechicalNo());

        deliveryRepository.save(dp);

        return "Delivery Partner Registered Successfully";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Assign delivery
    public Deliverypartner assignDelivery(Deliverypartner delivery) {
        delivery.setStatus("ASSIGNED");
        return deliveryRepository.save(delivery);
    }

    // Get all delivery partners
    public List<Deliverypartner> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

}
