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
    
    
    public String registerCustomer(CustomerRegDto dto){

        if(customerRepository.existsByMailid(dto.getMailid())){
            return "Customer already registered with this email";
        }

        Customer c = new Customer();
        c.setName(dto.getName());
        c.setMobileno(dto.getMobileno());
        c.setMailid(dto.getMailid());
        c.setGender(dto.getGender());

        customerRepository.save(c);

        return "Customer Registered Successfully";
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

//    public Customer getCustomerById(Long id) {
//        return customerRepository.findById(id).orElse(null);
//    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

	
}
