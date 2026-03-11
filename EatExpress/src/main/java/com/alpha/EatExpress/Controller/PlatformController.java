package com.alpha.EatExpress.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.EatExpress.entity.Coupon;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.Service.PlatformService;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @PostMapping("/createcoupon")
    public ResponseEntity<ResponceStructure<Coupon>> createCoupon(
            @RequestBody Coupon coupon){
        return platformService.createCoupon(coupon);
    }

    @DeleteMapping("/deletecoupon")
    public ResponseEntity<ResponceStructure<String>> deleteCoupon(
            @RequestParam Integer couponId){
        return platformService.deleteCoupon(couponId);
    }

    @PatchMapping("/updatecoupon")
    public ResponseEntity<ResponceStructure<Coupon>> updateCoupon(
            @RequestParam Integer couponId,
            @RequestParam String expiryDate){
        return platformService.updateCoupon(couponId, expiryDate);
    }

    @GetMapping("/findcoupon")
    public ResponseEntity<ResponceStructure<Coupon>> findCoupon(
            @RequestParam Integer couponId){
        return platformService.findCoupon(couponId);
    }
}