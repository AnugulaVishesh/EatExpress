package com.alpha.EatExpress.DTO;

public class RestaurantDTO {

    private String name;
    private Long mobno;
    private String mail;
    private String description;
    private LocationCoordinates coordinates;
    private int packagingFee;
    private String type;

    public RestaurantDTO() {
    }

    public RestaurantDTO(String name, Long mobno, String mail, String description,
                         LocationCoordinates coordinates, int packagingFee, String type) {
        this.name = name;
        this.mobno = mobno;
        this.mail = mail;
        this.description = description;
        this.coordinates = coordinates;
        this.packagingFee = packagingFee;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Long getMobno() {
        return mobno;
    }

    public String getMail() {
        return mail;
    }

    public String getDescription() {
        return description;
    }

    public LocationCoordinates getCoordinates() {
        return coordinates;
    }

    public int getPackagingFee() {
        return packagingFee;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobno(Long mobno) {
        this.mobno = mobno;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoordinates(LocationCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setPackagingFee(int packagingFee) {
        this.packagingFee = packagingFee;
    }

    public void setType(String type) {
        this.type = type;
    }
}