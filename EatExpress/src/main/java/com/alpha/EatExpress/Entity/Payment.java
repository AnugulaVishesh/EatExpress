package com.alpha.EatExpress.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double amount;
    private String type;
    private String status;
    @OneToOne
    private FoodOrder foodorder;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public FoodOrder getFoodorder() {
		return foodorder;
	}
	public void setFoodorder(FoodOrder foodorder) {
		this.foodorder = foodorder;
	}
	public Payment(int id, double amount, String type, String status, FoodOrder foodorder) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.status = status;
		this.foodorder = foodorder;
	}
	public Payment() {
		super();
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", amount=" + amount + ", type=" + type + ", status=" + status + "]";
	}
    
}
