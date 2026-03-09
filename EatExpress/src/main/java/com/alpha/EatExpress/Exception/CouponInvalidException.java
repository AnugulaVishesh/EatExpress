package com.alpha.EatExpress.Exception;

public class CouponInvalidException extends RuntimeException{
    public CouponInvalidException(String msg){
        super(msg);
    }
}