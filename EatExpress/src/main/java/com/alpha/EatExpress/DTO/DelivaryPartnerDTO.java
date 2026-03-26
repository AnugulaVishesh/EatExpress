package com.alpha.EatExpress.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DelivaryPartnerDTO {

    @NotBlank(message = "Delivery partner name cannot be empty")
    private String name;

    @NotNull(message = "Mobile number is required")
    @Min(value = 1000000000L, message = "Mobile number must be 10 digits")
    @Max(value = 9999999999L, message = "Mobile number must be 10 digits")
    private Long mob;

    @NotBlank(message = "Mail id cannot be empty")
    @Email(message = "Enter a valid email id")
    private String mail;

    @NotBlank(message = "Vehicle number cannot be empty")
    private String vechileno;

    public DelivaryPartnerDTO() {
    }

    public DelivaryPartnerDTO(String name, Long mob, String mail, String vechileno) {
        this.name = name;
        this.mob = mob;
        this.mail = mail;
        this.vechileno = vechileno;
    }

    public String getName() {
        return name;
    }

    public Long getMob() {
        return mob;
    }

    public String getMail() {
        return mail;
    }

    public String getVechileno() {
        return vechileno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMob(Long mob) {
        this.mob = mob;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setVechileno(String vechileno) {
        this.vechileno = vechileno;
    }
}