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
    @JoinColumn(name = "order_id")
    private FoodOrder order;
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
	public FoodOrder getOrder() {
		return order;
	}
	public void setOrder(FoodOrder order) {
		this.order = order;
	}
	public Payment(int id, double amount, String type, String status, FoodOrder order) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.status = status;
		this.order = order;
	}
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}
    
  
    

}
