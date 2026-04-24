package com.alpha.EatExpress.Exception;

public class CouponExpiredException extends RuntimeException{
    public CouponExpiredException(String msg){
        super(msg);
    } 

}