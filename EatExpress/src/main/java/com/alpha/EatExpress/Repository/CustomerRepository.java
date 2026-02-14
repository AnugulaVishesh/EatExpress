package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
