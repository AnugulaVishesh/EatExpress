package com.alpha.EatExpress.Exception;

public class CouponLimitExceededException extends RuntimeException{
    public CouponLimitExceededException (String msg){
        super(msg);
    }   
}