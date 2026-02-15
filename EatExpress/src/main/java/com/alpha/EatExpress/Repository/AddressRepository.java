package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.alpha.EatExpress.Entity.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	
}
