package com.alpha.EatExpress.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CustomerDTO {

    @NotBlank(message = "Customer name cannot be empty")
    private String name;

    @NotNull(message = "Mobile number is required")
    @Min(value = 1000000000L , message = "Mobile number must be 10 digits")
    @Max(value = 9999999999L , message = "Mobile number must be 10 digits")
    private Long mobno;

    @NotBlank(message = "Mail id cannot be empty")
    @Email(message = "Enter a valid email id")
    private String mailid;

    @NotBlank(message = "Gender cannot be empty")
    private String gender;

    private LocationCoordinates coordinates;
    private String city;
    private String area;
    private String street;
    private Integer pincode;

    public CustomerDTO() {}

    public CustomerDTO(String name, Long mobno, String mailid, String gender) {
        this.name = name;
        this.mobno = mobno;
        this.mailid = mailid;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public Long getMobno() {
        return mobno;
    }

    public String getMailid() {
        return mailid;
    }

    public String getGender() {
        return gender;
    }

    public LocationCoordinates getCoordinates() {
        return coordinates;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getStreet() {
        return street;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobno(Long mobno) {
        this.mobno = mobno;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCoordinates(LocationCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "CustomerDTO [name=" + name + ", mobno=" + mobno +
                ", mailid=" + mailid + ", gender=" + gender + "]";
    }
}
