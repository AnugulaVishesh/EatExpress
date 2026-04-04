package com.alpha.EatExpress.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.AcceptOrderDTO;
import com.alpha.EatExpress.DTO.DelivaryPartnerDTO;
import com.alpha.EatExpress.DTO.DirectionToCustomerDTO;
import com.alpha.EatExpress.DTO.DirectionToRestaurantDTO;
import com.alpha.EatExpress.DTO.FindPartnerDTO;
import com.alpha.EatExpress.DTO.UpdateLocationDTO;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Service.DeliveryPartnerService;
import com.alpha.EatExpress.entity.DeliveryPartner;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/deliveryPartner")
public class DelivaryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    // Register Delivery Partner
    @PostMapping("/register")
    public ResponseEntity<ResponceStructure<DeliveryPartner>> register(
            @RequestBody @Valid DelivaryPartnerDTO ddto) {

        return deliveryPartnerService.register(ddto);
    }

    // Find Delivery Partner
    @PostMapping("/find")
    public ResponseEntity<ResponceStructure<DeliveryPartner>> find(
            @RequestBody FindPartnerDTO dto) {

        return deliveryPartnerService.find(dto.getMob());
    }

    // Delete Delivery Partner
    @DeleteMapping("/delete")
    public ResponseEntity<ResponceStructure<String>> delete(
            @RequestBody FindPartnerDTO dto) {

        return deliveryPartnerService.delete(dto.getMob());
    }

    // Update Location
    @PostMapping("/updatelocation")
    public ResponseEntity<ResponceStructure<String>> updateLocation(
            @RequestBody UpdateLocationDTO dto) {

        return deliveryPartnerService.updateDeliveryPartnerLocation(
                dto.getPartnerid(),
                dto.getLatitude(),
                dto.getLongitude());
    }

    // Accept Order
    @PostMapping("/acceptOrder")
    public ResponseEntity<ResponceStructure<String>> acceptOrder(
            @RequestBody AcceptOrderDTO dto) {

        return deliveryPartnerService.acceptOrder(
                dto.getOrderid(),
                dto.getPartnerid());
    }

    // Direction to Restaurant
    @PostMapping("/getDirectionToRestaurant")
    public void getDirectionToRestaurant(
            @RequestBody DirectionToRestaurantDTO dto,
            HttpServletResponse response) throws IOException {

        deliveryPartnerService.getDirectionToRestaurant(
                dto.getPartnerId(),
                dto.getRestlat(),
                dto.getRestlong(),
                response);
    }

    // Direction to Customer
    @PostMapping("/getDirectionToCustomer")
    public void getDirectionToCustomer(
            @RequestBody DirectionToCustomerDTO dto,
            HttpServletResponse response) throws IOException {

        deliveryPartnerService.getDirectionToCustomer(
                dto.getRestlat(),
                dto.getRestlon(),
                dto.getCustlat(),
                dto.getCustlong(),
                response);
    }
}