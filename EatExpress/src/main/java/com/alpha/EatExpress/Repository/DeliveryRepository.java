package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Deliverypartner;

public interface DeliveryRepository extends JpaRepository<Deliverypartner, Long> {

	Deliverypartner findBymobno(Long mob);
	
}
