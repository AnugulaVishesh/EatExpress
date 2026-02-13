package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.FoodOrder;

public interface OrderRepository extends JpaRepository<FoodOrder, Long> {
}
