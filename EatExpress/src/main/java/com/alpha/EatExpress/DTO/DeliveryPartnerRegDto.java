package com.alpha.EatExpress.DTO;

public class DeliveryPartnerRegDto {

    private String name;
    private long mobno;
    private String emailid;
    private String vechicalNo;

    public DeliveryPartnerRegDto() {
    }

    public DeliveryPartnerRegDto(String name, long mobno, String emailid, String vechicalNo) {
        this.name = name;
        this.mobno = mobno;
        this.emailid = emailid;
        this.vechicalNo = vechicalNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMobno() {
        return mobno;
    }

    public void setMobno(long mobno) {
        this.mobno = mobno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getVechicalNo() {
        return vechicalNo;
    }

    public void setVechicalNo(String vechicalNo) {
        this.vechicalNo = vechicalNo;
    }
}
