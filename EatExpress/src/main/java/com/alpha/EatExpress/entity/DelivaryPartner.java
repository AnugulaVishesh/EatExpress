package com.alpha.EatExpress.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class DelivaryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private long mob;

    @Column(unique = true)
    private String mail;

    private double rating;

    private String status;

    private String vehicileno;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "deliveryPartner")
    private List<Order> orders;

    public DelivaryPartner() {}

    public DelivaryPartner(int id, String name, long mob, String mail,
                           double rating, String status, String vehicileno,
                           Address address, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.mob = mob;
        this.mail = mail;
        this.rating = rating;
        this.status = status;
        this.vehicileno = vehicileno;
        this.address = address;
        this.orders = orders;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public long getMob() { return mob; }

    public void setMob(long mob) { this.mob = mob; }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public double getRating() { return rating; }

    public void setRating(double rating) { this.rating = rating; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getVehicileno() { return vehicileno; }

    public void setVehicileno(String vehicileno) { this.vehicileno = vehicileno; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }

    public List<Order> getOrders() { return orders; }

    public void setOrders(List<Order> orders) { this.orders = orders; }

    @Override
    public String toString() {
        return "DelivaryPartner [id=" + id +
                ", name=" + name +
                ", mob=" + mob +
                ", mail=" + mail +
                ", rating=" + rating +
                ", status=" + status +
                ", vehicileno=" + vehicileno +
                ", address=" + address +
                ", orders=" + orders + "]";
    }
}