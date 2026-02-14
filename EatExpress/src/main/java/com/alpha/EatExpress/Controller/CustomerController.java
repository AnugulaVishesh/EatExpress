package com.alpha.EatExpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.DTO.CustomerRegDto;
import com.alpha.EatExpress.Entity.Customer;
import com.alpha.EatExpress.Service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/register")
	public String registerCustomer(@RequestBody CustomerRegDto dto) {
		return customerService.registerCustomer(dto);
	}

	@DeleteMapping
	public void deleteCustomer(@RequestParam Long mob) {
		customerService.deleteCustomer(mob);
	}
	
}
