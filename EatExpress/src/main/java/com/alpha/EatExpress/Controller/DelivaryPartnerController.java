package com.alpha.EatExpress.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.EatExpress.DTO.DelivaryPartnerDTO;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Service.DeliveryPartnerService;
import com.alpha.EatExpress.entity.DeliveryPartner;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/deliveryPartner")
public class DelivaryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @PostMapping("/register")
    public ResponseEntity<ResponceStructure<DeliveryPartner>> register(
            @RequestBody DelivaryPartnerDTO ddto){
        return deliveryPartnerService.register(ddto);
    }

    @GetMapping("/find")
    public ResponseEntity<ResponceStructure<DeliveryPartner>> find(
            @RequestParam long mob){
        return deliveryPartnerService.find(mob);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponceStructure<String>> delete(
            @RequestParam long mob){
        return deliveryPartnerService.delete(mob);
    }

    @PostMapping("/updatelocation")
    public ResponseEntity<ResponceStructure<String>> updateLocation(
            @RequestParam Integer partnerid,
            @RequestParam double latitude,
            @RequestParam double longitude){
        return deliveryPartnerService.updateDeliveryPartnerLocation(partnerid, latitude, longitude);
    }

    @PostMapping("/acceptOrder")
    public ResponseEntity<ResponceStructure<String>> acceptOrder(
            @RequestParam Integer orderid,
            @RequestParam Integer partnerid){
        return deliveryPartnerService.acceptOrder(orderid, partnerid);
    }

    @GetMapping("/getDirectionToRestaurant")
    public void getDirectionToRestaurant(
            @RequestParam Integer partnerId,
            @RequestParam double restlat,
            @RequestParam double restlong,
            HttpServletResponse response) throws IOException {

        deliveryPartnerService.getDirectionToRestaurant(partnerId, restlat, restlong, response);
    }

    @GetMapping("/getDirectionToCustomer")
    public void getDirectionToCustomer(
            @RequestParam double restlat,
            @RequestParam double restlon,
            @RequestParam double custlat,
            @RequestParam double custlong,
            HttpServletResponse response) throws IOException {

        deliveryPartnerService.getDirectionToCustomer(restlat, restlon, custlat, custlong, response);
    }
}