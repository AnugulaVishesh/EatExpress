package com.alpha.EatExpress.DTO;

public class DelivaryPartnerDTO {

    private String name;
    private long mob;
    private String mail;
    private String vechileno;

    public DelivaryPartnerDTO() {
    }

    public DelivaryPartnerDTO(String name, long mob, String mail, String vechileno) {
        this.name = name;
        this.mob = mob;
        this.mail = mail;
        this.vechileno = vechileno;
    }

    public String getName() {
        return name;
    }

    public long getMob() {
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

    public void setMob(long mob) {
        this.mob = mob;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setVechileno(String vechileno) {
        this.vechileno = vechileno;
    }
}