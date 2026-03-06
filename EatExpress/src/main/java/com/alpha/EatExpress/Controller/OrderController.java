package com.alpha.EatExpress.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Servicee.OrderService;
import com.alpha.EatExpress.DTO.OrderNeedConsentDTO;
import com.alpha.EatExpress.entity.Order;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

   
    @PostMapping("/placeorder")
    public ResponseEntity<ResponceStructure<OrderNeedConsentDTO>> placeOrder(
            @RequestParam long mobno){

        return orderService.placeOrder(mobno);
    }

    
    @PostMapping("/confirm")
    public ResponseEntity<ResponceStructure<Order>> confirmOrder(
            @RequestParam int orderId){

        return orderService.confirmOrder(orderId);
    }

    
    @PostMapping("/cancel")
    public ResponseEntity<ResponceStructure<Order>> cancelOrder(
            @RequestParam int orderId){

        return orderService.cancelOrder(orderId);
    }
}