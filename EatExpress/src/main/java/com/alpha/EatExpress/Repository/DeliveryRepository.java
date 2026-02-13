package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
