package com.alpha.EatExpress.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.Coupon;
import com.alpha.EatExpress.repository.CouponRedemptionRepository;
import com.alpha.EatExpress.repository.CouponRepository;

@Service
public class Couponservice {
	@Autowired
	private CouponRepository couponrepoo;
	
	@Autowired
	private CouponRedemptionRepository couponrederepoo;
	
    public ResponseEntity<ResponceStructure<Coupon>> createCoupon(Coupon coupon){

        Coupon savedCoupon = couponrepoo.save(coupon);

        ResponceStructure<Coupon> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Coupon Created Successfully");
        rs.setData(savedCoupon);

        return new ResponseEntity<>(rs,HttpStatus.CREATED);
    }
    
    

    public ResponseEntity<ResponceStructure<String>> deleteCoupon(Integer couponId){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        
        
        if(couponrederepoo.existsByCoupon(coupon)){
            throw new RuntimeException("Coupon already used by customers, cannot delete");
        }

        couponrepoo.delete(coupon);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Coupon Deleted Successfully");
        rs.setData("Deleted");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

 

    public ResponseEntity<ResponceStructure<Coupon>> updateCoupon(
            Integer couponId,
            String expiryDate){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        boolean used = couponrederepoo.existsByCoupon(coupon);

        
        if(!used){
            coupon.setExpiryDate(LocalDate.parse(expiryDate));
        }
        else{
            // If used → reduce maxCoupons
            if(coupon.getMaxCoupons() > 0){
                coupon.setMaxCoupons(coupon.getMaxCoupons() - 1);
            }
        }

        couponrepoo.save(coupon);

        ResponceStructure<Coupon> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Coupon Updated Successfully");
        rs.setData(coupon);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }



    public ResponseEntity<ResponceStructure<Coupon>> findCoupon(Integer couponId){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        ResponceStructure<Coupon> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Coupon Fetched Successfully");
        rs.setData(coupon);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

	
}