package com.alpha.EatExpress.entity;

import java.util.ArrayList;
import java.util.List;import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private long mobno;

    @Column(unique = true)
    private String mailid;

    private String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "customer")
    private List<Order> order;

    @ManyToMany(mappedBy = "customers")
    private List<Item> item;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<CartItem> cart = new ArrayList<>();

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public long getMobno() { return mobno; }

    public void setMobno(long mobno) { this.mobno = mobno; }

    public String getMailid() { return mailid; }

    public void setMailid(String mailid) { this.mailid = mailid; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }

    public List<Order> getOrder() { return order; }

    public void setOrder(List<Order> order) { this.order = order; }

    public List<Item> getItem() { return item; }

    public void setItem(List<Item> item) { this.item = item; }

    public List<CartItem> getCart() { return cart; }

    public void setCart(List<CartItem> cart) { this.cart = cart; }

    public Customer() {}

    public Customer(int id, String name, long mobno, String mailid, String gender,
                    Address address, List<Order> order, List<Item> item, List<CartItem> cart) {
        this.id = id;
        this.name = name;
        this.mobno = mobno;
        this.mailid = mailid;
        this.gender = gender;
        this.address = address;
        this.order = order;
        this.item = item;
        this.cart = cart;
    }
}