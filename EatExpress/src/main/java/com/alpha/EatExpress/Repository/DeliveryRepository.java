package com.alpha.EatExpress.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Deliverypartner;

public interface DeliveryRepository extends JpaRepository<Deliverypartner, Long> {

	 Optional<Deliverypartner> findBymobno(long mob);
}
