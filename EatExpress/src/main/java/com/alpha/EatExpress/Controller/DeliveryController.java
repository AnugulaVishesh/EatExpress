package com.alpha.EatExpress.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alpha.EatExpress.Entity.Deliverypartner;
import com.alpha.EatExpress.Service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/assign")
    public Deliverypartner assignDelivery(@RequestBody Deliverypartner delivery) {
        return deliveryService.assignDelivery(delivery);
    }

    @GetMapping
    public List<Deliverypartner> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }
}
