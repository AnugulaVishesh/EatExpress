package com.alpha.EatExpress.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.DeliveryPartnerRegDto;
import com.alpha.EatExpress.Entity.Deliverypartner;
import com.alpha.EatExpress.Service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;
    
    @PostMapping("/register")
    public String registerDeliveryPartner(@RequestBody DeliveryPartnerRegDto dto){
        return deliveryService.registerDeliveryPartner(dto);
    }
    
    

    
    
    
    
    
    
    
    
    
    
  
}
