package com.alpha.EatExpress.Service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    public Order placeOrder(Order order) {

        // ✅ Save order
        Order savedOrder = orderRepository.save(order);

        // ✅ Get customer email
        String customerEmail = savedOrder.getCustomer().getMailid();
        String customerName = savedOrder.getCustomer().getName();

        // ✅ Get items list (convert to string)
        String items = savedOrder.getItems()
                .stream()
                .map(item -> item.getName())
                .collect(Collectors.joining(", "));

        // ✅ Email Subject
        String subject = "Order Confirmation - EatExpress";

        // ✅ Email Body (Dynamic)
        String body = "Hello " + customerName + ",\n\n"
                + "Your order has been placed successfully!\n\n"
                + "Order ID: " + savedOrder.getId() + "\n"
                + "Restaurant: " + savedOrder.getRestaurant().getName() + "\n"
                + "Items: " + items + "\n"
                + "Delivery Address: " + savedOrder.getDeliveryAddress() + "\n"
                + "Final Amount: ₹" + savedOrder.getFinalAmount() + "\n\n"
                + "Estimated Time: " + savedOrder.getEstimatedTime() + "\n\n"
                + "Thank you for ordering with EatExpress!";

        // ✅ Send Mail
        emailService.sendMail(customerEmail, subject, body);

        return savedOrder;
    }
}