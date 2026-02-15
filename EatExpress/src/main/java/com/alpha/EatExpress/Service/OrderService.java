package com.alpha.EatExpress.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alpha.EatExpress.Repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

   
}
