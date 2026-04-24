package com.alpha.EatExpress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.EatExpress.entity.Coupon;
import com.alpha.EatExpress.entity.CouponRedemption;
import com.alpha.EatExpress.entity.Customer;

@Repository
public interface CouponRedemptionRepository extends JpaRepository<CouponRedemption, Integer> {

    Optional<CouponRedemption> findByCouponAndCustomer(Coupon coupon, Customer customer);

    boolean existsByCoupon(Coupon coupon);

}