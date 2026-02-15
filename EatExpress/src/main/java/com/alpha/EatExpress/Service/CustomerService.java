package com.alpha.EatExpress.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.CustomerRegDto;
import com.alpha.EatExpress.Entity.Customer;
import com.alpha.EatExpress.Entity.ResponseStructure;
import com.alpha.EatExpress.Exception.CustomerNotFound;
import com.alpha.EatExpress.Repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public String registerCustomer(CustomerRegDto dto) {

		Customer c = new Customer();
		c.setName(dto.getName());
		c.setMobileno(dto.getMobileno());
		c.setMailid(dto.getMailid());
		c.setGender(dto.getGender());

		customerRepository.save(c);

		return "Customer Registered Successfully";
	}

	public void deleteCustomer(Long mob) {
		
		customerRepository.deleteBymobileno(mob);
	
	
	}

	public ResponseStructure<Customer> findBymobileno(long mob) {
		ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
		
		Customer c= customerRepository.findBymobileno(mob).orElseThrow(() -> new CustomerNotFound());
		
		rs.setStatuscode(200);
		rs.setMessage("Customer Found Successfully");
		rs.setData(c);
		
		return rs;
		
		
	}

}
