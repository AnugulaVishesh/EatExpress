package com.alpha.EatExpress.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RestaurantDTO {

    @NotBlank(message = "Restaurant name should not be empty")
    private String name;

    @NotNull(message = "Mobile number is required")
    @Min(value = 1000000000L, message = "Mobile number must be 10 digits")
    @Max(value = 9999999999L, message = "Mobile number must be 10 digits")
    private Long mobno;

    @NotBlank(message = "Mail id cannot be empty")
    @Email(message = "Enter a valid email id")
    private String mail;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Coordinates cannot be null")
    @Valid
    private LocationCoordinates coordinates;

    @Min(value = 0, message = "Packaging fee cannot be negative")
    private int packagingFee;

    @NotBlank(message = "Restaurant type cannot be empty")
    private String type;

    public RestaurantDTO() {}

    public RestaurantDTO(String name, Long mobno, String mail,
                         String description, LocationCoordinates coordinates,
                         int packagingFee, String type) {
        this.name = name;
        this.mobno = mobno;
        this.mail = mail;
        this.description = description;
        this.coordinates = coordinates;
        this.packagingFee = packagingFee;
        this.type = type;
    }

    public String getName() { return name; }
    public Long getMobno() { return mobno; }
    public String getMail() { return mail; }
    public String getDescription() { return description; }
    public LocationCoordinates getCoordinates() { return coordinates; }
    public int getPackagingFee() { return packagingFee; }
    public String getType() { return type; }

    public void setName(String name) { this.name = name; }
    public void setMobno(Long mobno) { this.mobno = mobno; }
    public void setMail(String mail) { this.mail = mail; }
    public void setDescription(String description) { this.description = description; }
    public void setCoordinates(LocationCoordinates coordinates) { this.coordinates = coordinates; }
    public void setPackagingFee(int packagingFee) { this.packagingFee = packagingFee; }
    public void setType(String type) { this.type = type; }
}