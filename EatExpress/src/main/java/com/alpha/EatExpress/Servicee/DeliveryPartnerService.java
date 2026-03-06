package com.alpha.EatExpress.Servicee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.DelivaryPartnerDTO;
import com.alpha.EatExpress.Exception.DeliveryPartnerNotFoundException;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.DelivaryPartner;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.repository.DelivaryPartnerRepo;
import com.alpha.EatExpress.repository.OrderRepo;

@Service
public class DeliveryPartnerService {

    @Autowired
    private DelivaryPartnerRepo delivarypartnerrepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public ResponceStructure<DelivaryPartner> saveDP(DelivaryPartnerDTO dpdto) {

        DelivaryPartner dp = new DelivaryPartner();
        dp.setName(dpdto.getName());
        dp.setMob(dpdto.getMob());
        dp.setMail(dpdto.getMail());
        dp.setVehicileno(dpdto.getVechileno());

        DelivaryPartner saved = delivarypartnerrepo.save(dp);

        ResponceStructure<DelivaryPartner> response = new ResponceStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Delivery Partner Registered Successfully");
        response.setData(saved);

        return response;
    }

    public boolean acceptOrder(Integer orderid, Integer partnerid) {

        Order order = orderRepo.findById(orderid)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        DelivaryPartner deliveryPartner = delivarypartnerrepo.findById(partnerid)
                .orElseThrow(() -> new RuntimeException("Delivery Partner not found"));

        String lockKey = "order_lock_" + orderid;

        Boolean locked = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, partnerid.toString());

        if (Boolean.TRUE.equals(locked)) {

            order.setDeliveryPartner(deliveryPartner);
            order.setStatus("ASSIGNED");

            orderRepo.save(order);

            redisTemplate.delete("order:" + orderid);

            return true;
        }

        return false;
    }

    public ResponseEntity<ResponceStructure<Order>> pickupOrder(Integer orderid) {

        Order order = orderRepo.findById(orderid)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("PICKED");
        orderRepo.save(order);

        ResponceStructure<Order> response = new ResponceStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Order Picked Successfully");
        response.setData(order);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ResponceStructure<Order>> startDelivery(Integer orderid) {

        Order order = orderRepo.findById(orderid)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("ON_THE_WAY");
        orderRepo.save(order);

        ResponceStructure<Order> response = new ResponceStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Delivery Started");
        response.setData(order);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ResponceStructure<Order>> markDelivered(Integer orderid, String otp) {

        Order order = orderRepo.findById(orderid)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        int enteredOtp = Integer.parseInt(otp);

        if (order.getOtp() != enteredOtp) {
            throw new RuntimeException("Invalid OTP");
        }

        order.setStatus("DELIVERED");
        orderRepo.save(order);

        ResponceStructure<Order> response = new ResponceStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Order Delivered Successfully");
        response.setData(order);

        return ResponseEntity.ok(response);
    }
}