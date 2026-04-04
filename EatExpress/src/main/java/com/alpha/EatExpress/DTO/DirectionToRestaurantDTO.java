package com.alpha.EatExpress.DTO;

public class DirectionToRestaurantDTO {

    private Integer partnerId;
    private double restlat;
    private double restlong;

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public double getRestlat() {
        return restlat;
    }

    public void setRestlat(double restlat) {
        this.restlat = restlat;
    }

    public double getRestlong() {
        return restlong;
    }

    public void setRestlong(double restlong) {
        this.restlong = restlong;
    }
}