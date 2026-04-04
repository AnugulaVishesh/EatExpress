package com.alpha.EatExpress.DTO;

public class DirectionToCustomerDTO {

    private double restlat;
    private double restlon;
    private double custlat;
    private double custlong;

    public double getRestlat() {
        return restlat;
    }

    public void setRestlat(double restlat) {
        this.restlat = restlat;
    }

    public double getRestlon() {
        return restlon;
    }

    public void setRestlon(double restlon) {
        this.restlon = restlon;
    }

    public double getCustlat() {
        return custlat;
    }

    public void setCustlat(double custlat) {
        this.custlat = custlat;
    }

    public double getCustlong() {
        return custlong;
    }

    public void setCustlong(double custlong) {
        this.custlong = custlong;
    }
}