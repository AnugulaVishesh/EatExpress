package com.alpha.EatExpress.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alpha.EatExpress.Entity.Deliverypartner;
import com.alpha.EatExpress.Repository.DeliveryRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Deliverypartner assignDelivery(Deliverypartner delivery) {
        delivery.setStatus("ASSIGNED");
        return deliveryRepository.save(delivery);
    }

    public List<Deliverypartner> getAllDeliveries() {
        return deliveryRepository.findAll();
    }
}
