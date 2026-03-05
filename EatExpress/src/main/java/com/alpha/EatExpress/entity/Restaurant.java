package com.alpha.EatExpress.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String mail;

    @Column(unique = true)
    private long mobno;

    private String status;

    private double ratings;

    private String description;

    private int packagingFee;

    private String type;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Item> menuItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public Restaurant() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public long getMobno() {
        return mobno;
    }

    public String getStatus() {
        return status;
    }

    public double getRatings() {
        return ratings;
    }

    public String getDescription() {
        return description;
    }

    public int getPackagingFee() {
        return packagingFee;
    }

    public String getType() {
        return type;
    }

    public List<Item> getMenuItems() {
        return menuItems;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMobno(long mobno) {
        this.mobno = mobno;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPackagingFee(int packagingFee) {
        this.packagingFee = packagingFee;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMenuItems(List<Item> menuItems) {
        this.menuItems = menuItems;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}