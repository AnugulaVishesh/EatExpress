package com.alpha.EatExpress.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String restaurant;
    private String status;
    private double cost;
    private LocalDateTime date;
    private String otp;
    private double estimatedTime;
    private double distance;
    private double discount;
    private String specialRequest;
    private String deliveryInstruction;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public double getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(double estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getSpecialRequest() {
		return specialRequest;
	}
	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}
	public String getDeliveryInstruction() {
		return deliveryInstruction;
	}
	public void setDeliveryInstruction(String deliveryInstruction) {
		this.deliveryInstruction = deliveryInstruction;
	}
	public FoodOrder(Long id, String restaurant, String status, double cost, LocalDateTime date, String otp,
			double estimatedTime, double distance, double discount, String specialRequest, String deliveryInstruction) {
		super();
		this.id = id;
		this.restaurant = restaurant;
		this.status = status;
		this.cost = cost;
		this.date = date;
		this.otp = otp;
		this.estimatedTime = estimatedTime;
		this.distance = distance;
		this.discount = discount;
		this.specialRequest = specialRequest;
		this.deliveryInstruction = deliveryInstruction;
	}
	@Override
	public String toString() {
		return "FoodOrder [id=" + id + ", restaurant=" + restaurant + ", status=" + status + ", cost=" + cost
				+ ", date=" + date + ", otp=" + otp + ", estimatedTime=" + estimatedTime + ", distance=" + distance
				+ ", discount=" + discount + ", specialRequest=" + specialRequest + ", deliveryInstruction="
				+ deliveryInstruction + "]";
	}
}
    

  