package com.alpha.EatExpress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.EatExpress.entity.DelivaryPartner;
@Repository
public interface DelivaryPartnerRepo extends JpaRepository<DelivaryPartner, Integer> {
	
	Optional<DelivaryPartner> findByMob(long mob);

	
	
}
