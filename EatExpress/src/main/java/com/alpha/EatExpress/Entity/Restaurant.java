package com.alpha.EatExpress.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "mobno",unique = true)
    private String mobno;

    @Column(name = "mailid",unique = true)
    private String mailid;

    private String address;


    @OneToMany
    private List<Item> menu;

    private String status;        
    private String rating;
    private String description;

    @OneToMany
    private List<FoodOrder> foodOrders;

    @Column(name = "packaging_fee")
    private double packagingfee;

    private String type; 

    // Default Constructor
    public Restaurant() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    @Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", mobno=" + mobno + ", mailid=" + mailid + ", address="
				+ address + ", menu=" + menu + ", status=" + status + ", rating=" + rating + ", description="
				+ description + ", packagingfee=" + packagingfee + ", type=" + type + "]";
	}

	public void setId(Long id) {
        this.id = id;
    }

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

    public List<Item> getMenu() {
        return menu;
    }

    public void setMenu(List<Item> menu) {
        this.menu = menu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
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
