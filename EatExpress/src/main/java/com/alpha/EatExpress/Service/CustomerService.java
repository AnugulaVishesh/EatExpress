package com.alpha.EatExpress.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.CustomerRegDto;
import com.alpha.EatExpress.Entity.Customer;
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
		// TODO Auto-generated method stub

	}

}
