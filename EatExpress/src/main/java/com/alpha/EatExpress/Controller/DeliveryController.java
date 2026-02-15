package com.alpha.EatExpress.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.DeliveryPartnerRegDto;
import com.alpha.EatExpress.Entity.Deliverypartner;
import com.alpha.EatExpress.Entity.ResponseStructure;
import com.alpha.EatExpress.Service.DeliveryService;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;
    
    @PostMapping("/register")
    public String registerDeliveryPartner(@RequestBody DeliveryPartnerRegDto dto){
        return deliveryService.registerDeliveryPartner(dto);
    }
    
    @GetMapping("/findDeliverypartner")
    public ResponseStructure<Deliverypartner> findDeliveryPartner(@RequestParam long mob) {
    	
    return	deliveryService.findBymobno(mob);
    	
    }

    
    
    
    
    
    
    
    
    
    
  
}
