package com.alpha.EatExpress.Servicee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.Exception.CustomerNotFoundException;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.CartItem;
import com.alpha.EatExpress.entity.Customer;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;
import com.alpha.EatExpress.repository.CustomerRepo;
import com.alpha.EatExpress.repository.OrderRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderRepo orderRepo;
    
//    @Autowired
//    private RedisService redisService;
    @Autowired
    private CustomerService customerService;
    

    public ResponseEntity<ResponceStructure<Order>> placeOrder(long mobno) {

		Customer customer = customerService.findByMobno(mobno).getBody().getData();

        if (customer == null) {
            throw new CustomerNotFoundException(
                    "Customer not found with mobile: " + mobno);
        }

        List<CartItem> cart = customer.getCart();

        if (cart == null || cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus("PLACED");
        order.setDate(LocalDate.now().toString());

        // Restaurant (because you restricted cart to single restaurant)
        Restaurant restaurant =
                cart.get(0).getItem().getRestaurant();

        order.setRestaurant(restaurant);
        order.setPickupaddress(
                restaurant.getAddress().getStreet());

        order.setDelivaryAddress(
                customer.getAddress().getStreet());

        int totalCost = 0;
        List<Item> orderItems = new ArrayList<>();

        for (CartItem ci : cart) {
            totalCost += ci.getItem().getPrice()
                    * ci.getQuantity();

            orderItems.add(ci.getItem());
        }

        order.setCost(totalCost);
        order.setItem(orderItems);

        int otp = (int)(Math.random() * 9000) + 1000;
        order.setOtp(otp);
        
        
       

        Order savedOrder = orderRepo.save(order);

        // Clear cart after placing order
        cart.clear();
        customerRepo.save(customer);

        ResponceStructure<Order> rs =
                new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Order Placed Successfully");
        rs.setData(savedOrder);

        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }
}