package com.alpha.EatExpress.Entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "deliveries")
public class Deliverypartner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer  id;
    private String name;
    private long mobno;
    private String rating;
    @OneToOne
    private Address address;
    @OneToMany
    private List<FoodOrder> order;
    private String vechicalNo;
    private String status;
	public Deliverypartner(Integer id, String name, long mobno, String rating, Address address, List<FoodOrder> order,
			String vechicalNo, String status) {
		super();
		this.id = id;
		this.name = name;
		this.mobno = mobno;
		this.rating = rating;
		this.address = address;
		this.order = order;
		this.vechicalNo = vechicalNo;
		this.status = status;
	}
	public Deliverypartner() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<FoodOrder> getOrder() {
		return order;
	}
	public void setOrder(List<FoodOrder> order) {
		this.order = order;
	}
	public String getVechicalNo() {
		return vechicalNo;
	}
	public void setVechicalNo(String vechicalNo) {
		this.vechicalNo = vechicalNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Deliverypartner [id=" + id + ", name=" + name + ", mobno=" + mobno + ", rating=" + rating + ", address="
				+ address + ", order=" + order + ", vechicalNo=" + vechicalNo + ", status=" + status + "]";
	}
    
    
    

    
}
