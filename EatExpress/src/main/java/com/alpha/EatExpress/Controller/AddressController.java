package com.alpha.EatExpress.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alpha.EatExpress.Entity.Address;
import com.alpha.EatExpress.Repository.AddressRepository;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping
    public Address saveAddress(@RequestBody Address address) {
        return addressRepository.save(address);
    }

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}
