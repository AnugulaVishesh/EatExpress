package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
