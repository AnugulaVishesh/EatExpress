package com.alpha.EatExpress.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.alpha.EatExpress.Entity.Customer;

import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findBymobileno(Long mob);
	
    @Transactional
    @Modifying
	void deleteBymobileno(Long mob);

	
	
}
