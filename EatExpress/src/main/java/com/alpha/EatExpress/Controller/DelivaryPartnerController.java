package com.alpha.EatExpress.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.DelivaryPartnerDTO;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Servicee.DeliveryPartnerService;
import com.alpha.EatExpress.Servicee.RedisService;
import com.alpha.EatExpress.entity.DelivaryPartner;
import com.alpha.EatExpress.entity.Order;

@RestController
@RequestMapping("/delivarypartner")
public class DelivaryPartnerController {

    @Autowired
    private DeliveryPartnerService dpservice;

    @Autowired
    private RedisService redisService;

    @PostMapping("/register")
    public ResponseEntity<ResponceStructure<DelivaryPartner>> saveDP(
            @RequestBody DelivaryPartnerDTO dpdto){

        ResponceStructure<DelivaryPartner> response = dpservice.saveDP(dpdto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/updatelocation")
    public String updateDplocation(
            @RequestParam Integer dpid,
            @RequestParam double latitude,
            @RequestParam double longitude){

        return redisService.updateDPloc(dpid, latitude, longitude);
    }

    @PostMapping("/accept-order")
    public boolean acceptOrder(
            @RequestParam Integer orderid,
            @RequestParam Integer partnerid){

        return dpservice.acceptOrder(orderid, partnerid);
    }

    @PostMapping("/pickup-order")
    public ResponseEntity<ResponceStructure<Order>> pickupOrder(
            @RequestParam Integer orderid){

        return dpservice.pickupOrder(orderid);
    }

    @PostMapping("/start-delivery")
    public ResponseEntity<ResponceStructure<Order>> startDelivery(
            @RequestParam Integer orderid){

        return dpservice.startDelivery(orderid);
    }

    @PostMapping("/mark-delivered")
    public ResponseEntity<ResponceStructure<Order>> markDelivered(
            @RequestParam Integer orderid,
            @RequestParam String otp){

        return dpservice.markDelivered(orderid, otp);
    }
}