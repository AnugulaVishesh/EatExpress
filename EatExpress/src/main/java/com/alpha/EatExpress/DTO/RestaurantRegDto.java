package com.alpha.EatExpress.DTO;

public class RestaurantRegDto {

    private String name;
    private String mobno;
    private String mailid;
    private String address;
    private String description;
    private double packagingfee;
    private String type;

    public RestaurantRegDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPackagingfee() {
        return packagingfee;
    }

    public void setPackagingfee(double packagingfee) {
        this.packagingfee = packagingfee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
