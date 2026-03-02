package com.alpha.EatExpress.Controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.EatExpress.DTO.CustomerDto;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Servicee.CustomerService;
import com.alpha.EatExpress.Servicee.OrderService;
import com.alpha.EatExpress.entity.Customer;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerservice;
    @Autowired
    private OrderService orderService;
    @PostMapping("/register")
    public ResponseEntity<ResponceStructure<Customer>> saveCustomer(
             @RequestBody CustomerDto cdto) {

        return customerservice.saveCustomer(cdto);
    }
        @GetMapping("/find/{mobno}")
        public ResponseEntity<ResponceStructure<Customer>> find(@PathVariable("mobno") long mobno) {
            return customerservice.findByMobno(mobno);
        }

        @DeleteMapping("/delete/{mobno}")
        public ResponseEntity<ResponceStructure<String>> delete(@PathVariable("mobno") long mobno) {
            return customerservice.deleteByMobno(mobno);
        }
        
        
        @GetMapping("/searchitemorrestaurant")
        public ResponseEntity<ResponceStructure<List<Restaurant>>> 
        searchItemOrRestaurant(
                @RequestParam long custmob,
                @RequestParam String searchkey) {

            return customerservice.searchItemOrRestaurant(custmob, searchkey);
        }

        
        @PostMapping("/addtocart")
        public ResponseEntity<ResponceStructure<String>> addToCart(
                @RequestParam long customermobno,
                @RequestParam int itemid,
                @RequestParam int quantity) {

            return customerservice.addToCart(
                    customermobno, itemid, quantity);
        }
        @PostMapping("/placeorder")
        public ResponseEntity<ResponceStructure<Order>>
        placeOrder(@RequestBody Map<String, Long> request) {

            long mobno = request.get("mobno");
            return orderService.placeOrder(mobno);
        }
 

        
        
    }

