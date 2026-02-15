package com.alpha.EatExpress.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Column(name = "mobileno", unique = true)
	private long mobileno;

	@Column(name = "mailid", unique = true)
	private String mailid;

	private String gender;

	private String address;
	@OneToMany
	private List<FoodOrder> orders;
	@OneToMany
	private List<Item> cart;

	public Customer() {
	}

	public Customer(Long id, String name, long mobileno, String mailid, String gender, String address,
			List<FoodOrder> orders, List<Item> cart) {
		super();
		this.id = id;
		this.name = name;
		this.mobileno = mobileno;
		this.mailid = mailid;
		this.gender = gender;
		this.address = address;
		this.orders = orders;
		this.cart = cart;
	}

	public Long getId() {
		return id;
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

	public long getMobileno() {
		return mobileno;
	}

	public void setMobileno(long mobileno) {
		this.mobileno = mobileno;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<FoodOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<FoodOrder> orders) {
		this.orders = orders;
	}

	public List<Item> getCart() {
		return cart;
	}

	public void setCart(List<Item> cart) {
		this.cart = cart;
	}

}