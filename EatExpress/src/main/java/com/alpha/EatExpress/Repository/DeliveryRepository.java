package com.alpha.EatExpress.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.alpha.EatExpress.Entity.Deliverypartner;

import jakarta.transaction.Transactional;

public interface DeliveryRepository extends JpaRepository<Deliverypartner, Long> {

	 Optional<Deliverypartner> findBymobno(long mob);
	 @Transactional
	 @Modifying
	void deleteBymobno(Long mob);
}