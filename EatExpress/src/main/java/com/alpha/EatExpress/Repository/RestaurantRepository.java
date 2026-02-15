package com.alpha.EatExpress.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.alpha.EatExpress.Entity.Restaurant;

import jakarta.transaction.Transactional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	boolean existsByMailid(String mailid);

    @Transactional
    @Modifying
	void deleteByMobno(Long mob);

    
    Optional<Restaurant> findBymobno(Long mob);
}
