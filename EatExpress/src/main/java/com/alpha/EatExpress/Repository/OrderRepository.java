package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
