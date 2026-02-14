package com.alpha.EatExpress.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.CustomerRegDto;
import com.alpha.EatExpress.Entity.Customer;
import com.alpha.EatExpress.Service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public String registerCustomer(@RequestBody CustomerRegDto dto){
        return customerService.registerCustomer(dto);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

//    @GetMapping("/{id}")
//    public Customer getCustomerById(@PathVariable Long id) {
//        return customerService.getCustomerById(id);
//    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }
}
