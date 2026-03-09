package com.alpha.EatExpress.DTO;

public class CustomerDTO {

    private String name;
    private Long mobno;
    private String mailid;
    private String gender;

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

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public CustomerDTO(String name, long mobno, String mailid, String gender) {
        this.name = name;
        this.mobno = mobno;
        this.mailid = mailid;
        this.gender = gender;
    }

    public CustomerDTO() {
    }

    @Override
    public String toString() {
        return "CustomerDto [name=" + name + ", mobno=" + mobno + ", mailid=" + mailid + ", gender=" + gender + "]";
    }
}