package com.alpha.EatExpress.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alpha.EatExpress.Entity.FoodOrder;
import com.alpha.EatExpress.Repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public FoodOrder placeOrder(FoodOrder order) {
        order.setStatus("PLACED");
        return orderRepository.save(order);
    }

    public List<FoodOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public FoodOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
