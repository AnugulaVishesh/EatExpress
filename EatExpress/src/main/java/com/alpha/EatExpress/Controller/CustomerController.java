package com.alpha.EatExpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.CartWithCouponsDTO;
import com.alpha.EatExpress.DTO.CustomerDTO;
import com.alpha.EatExpress.DTO.OrderNeedConsentDTO;
import com.alpha.EatExpress.entity.Customer;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<ResponceStructure<Customer>> register(@RequestBody CustomerDTO cdto){
        return customerService.register(cdto);
    }

    @GetMapping("/find")
    public ResponseEntity<ResponceStructure<Customer>> find(@RequestParam long mobno){
        return customerService.findCustomer(mobno);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponceStructure<String>> delete(@RequestParam long mobno){
        return customerService.deleteCustomer(mobno);
    }

    @PostMapping("/addToCart")
    public ResponseEntity<ResponceStructure<String>> addToCart(
            @RequestParam long mobno,
            @RequestParam int itemid,
            @RequestParam int quantity){
        return customerService.addToCart(mobno,itemid,quantity);
    }

    @GetMapping("/getCart")
    public ResponseEntity<ResponceStructure<CartWithCouponsDTO>> getCart(@RequestParam long mobno){
        return customerService.getCart(mobno);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<ResponceStructure<OrderNeedConsentDTO>> placeOrder(
            @RequestParam long mobno,
            @RequestParam String paymentType,
            @RequestParam String addressType,
            @RequestParam String specialRequest,
            @RequestParam(required = false) Integer couponId){

        return customerService.placeOrder(mobno,paymentType,addressType,specialRequest,couponId);
    }

    @PostMapping("/confirmorder")
    public ResponseEntity<ResponceStructure<String>> confirmOrder(@RequestParam int orderid){
        return customerService.confirmPlacingOrder(orderid);
    }

  
    @PutMapping("/cancelOrder")
    public ResponseEntity<ResponceStructure<Order>> cancelOrder(
            @RequestParam Long phone,
            @RequestParam Long orderId){

        return customerService.cancelOrder(phone, orderId);
    }

    @GetMapping("/searchitemorrestaurant")
    public ResponseEntity<ResponceStructure<List<Restaurant>>> searchItemOrRestaurant(
            @RequestParam long mobno,
            @RequestParam String searchkey){

        return customerService.searchItemOrRestaurant(mobno,searchkey);
    }

    @DeleteMapping("/removeitemfromcart")
    public ResponseEntity<ResponceStructure<String>> removeItemFromCart(
            @RequestParam long customermobno,
            @RequestParam long restmob,
            @RequestParam int itemid){

        return customerService.removeItemFromCart(customermobno,restmob,itemid);
    }
}