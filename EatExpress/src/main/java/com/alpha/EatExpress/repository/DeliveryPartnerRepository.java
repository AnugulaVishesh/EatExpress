package com.alpha.EatExpress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.EatExpress.entity.DeliveryPartner;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Integer> {

    Optional<DeliveryPartner> findByMob(long mob);

}