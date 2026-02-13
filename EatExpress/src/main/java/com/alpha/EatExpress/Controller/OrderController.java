package com.alpha.EatExpress.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alpha.EatExpress.Entity.FoodOrder;
import com.alpha.EatExpress.Service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public FoodOrder placeOrder(@RequestBody FoodOrder order) {
        return orderService.placeOrder(order);
    }

    @GetMapping
    public List<FoodOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public FoodOrder getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
