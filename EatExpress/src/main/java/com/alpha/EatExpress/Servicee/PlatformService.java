package com.alpha.EatExpress.Servicee;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.entity.Coupon;
import com.alpha.EatExpress.Exception.CouponInvalidException;
import com.alpha.EatExpress.Exception.CouponNotFoundException;
import com.alpha.EatExpress.repository.CouponRedemptionRepository;
import com.alpha.EatExpress.repository.CouponRepository;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;

@Service
public class PlatformService {

    private final CouponRepository couponRepo;
    private final CouponRedemptionRepository couponRedemptionRepo;

    public PlatformService(CouponRepository couponRepo,
                           CouponRedemptionRepository couponRedemptionRepo) {
        this.couponRepo = couponRepo;
        this.couponRedemptionRepo = couponRedemptionRepo;
    }

    // CREATE COUPON
    public ResponseEntity<ResponceStructure<Coupon>> createCoupon(Coupon coupon){

        Coupon savedCoupon = couponRepo.save(coupon);

        ResponceStructure<Coupon> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Coupon Created Successfully");
        rs.setData(savedCoupon);

        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }

    // DELETE COUPON
    public ResponseEntity<ResponceStructure<String>> deleteCoupon(Integer couponId){

        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found"));

        if(couponRedemptionRepo.existsByCoupon(coupon)){
            throw new CouponInvalidException(
                    "Coupon already used by customers, cannot delete");
        }

        couponRepo.delete(coupon);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Coupon Deleted Successfully");
        rs.setData("Deleted");

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // UPDATE COUPON
    public ResponseEntity<ResponceStructure<Coupon>> updateCoupon(
            Integer couponId,
            String expiryDate){

        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found"));

        boolean used = couponRedemptionRepo.existsByCoupon(coupon);

        if(!used){
            coupon.setExpiryDate(LocalDate.parse(expiryDate));
        } else {
            if(coupon.getMaxCoupons() > 0){
                coupon.setMaxCoupons(coupon.getMaxCoupons() - 1);
            }
        }

        Coupon updatedCoupon = couponRepo.save(coupon);

        ResponceStructure<Coupon> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Coupon Updated Successfully");
        rs.setData(updatedCoupon);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // FIND COUPON
    public ResponseEntity<ResponceStructure<Coupon>> findCoupon(Integer couponId){

        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found"));

        ResponceStructure<Coupon> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Coupon Fetched Successfully");
        rs.setData(coupon);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}