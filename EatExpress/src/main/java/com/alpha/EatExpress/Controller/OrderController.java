package com.alpha.EatExpress.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.OrderNeedConsentDTO;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Servicee.OrderService;
import com.alpha.EatExpress.entity.Order;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/{mobno}")
    public ResponseEntity<ResponceStructure<OrderNeedConsentDTO>> placeOrder(
            @PathVariable long mobno) {

        return orderService.placeOrder(mobno);
    }

    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<ResponceStructure<Order>> confirmOrder(
            @PathVariable int orderId) {

        return orderService.confirmOrder(orderId);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<ResponceStructure<Order>> cancelOrder(
            @PathVariable int orderId) {

        return orderService.cancelOrder(orderId);
    }
}