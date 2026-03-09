package com.alpha.EatExpress.Exception;

public class CouponNotFoundException extends RuntimeException{
    public CouponNotFoundException(String msg){
        super(msg);
    }
}