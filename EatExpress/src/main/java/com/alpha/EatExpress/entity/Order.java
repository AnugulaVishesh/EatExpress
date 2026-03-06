package com.alpha.EatExpress.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String status;

    private BigDecimal cost;

    private int otp;

    private String pickupAddress;

    private String deliveryAddress;

    private String estimatedTime;

    private double distance;

    private int discount;

    private String coupons;

    private String specialRequest;

    private String deliveryInstructions;

    private String date;

    private BigDecimal orderPrice;

    private BigDecimal deliveryCharges;

    private BigDecimal packagingFees;

    private BigDecimal tax;

    private BigDecimal platformFees;

    @ManyToOne
    @JoinColumn(name = "delivery_partner_id")
    private DelivaryPartner deliveryPartner;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    public Order() {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public BigDecimal getCost() { return cost; }

    public void setCost(BigDecimal cost) { this.cost = cost; }

    public int getOtp() { return otp; }

    public void setOtp(int otp) { this.otp = otp; }

    public String getPickupAddress() { return pickupAddress; }

    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }

    public String getDeliveryAddress() { return deliveryAddress; }

    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getEstimatedTime() { return estimatedTime; }

    public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }

    public double getDistance() { return distance; }

    public void setDistance(double distance) { this.distance = distance; }

    public int getDiscount() { return discount; }

    public void setDiscount(int discount) { this.discount = discount; }

    public String getCoupons() { return coupons; }

    public void setCoupons(String coupons) { this.coupons = coupons; }

    public String getSpecialRequest() { return specialRequest; }

    public void setSpecialRequest(String specialRequest) { this.specialRequest = specialRequest; }

    public String getDeliveryInstructions() { return deliveryInstructions; }

    public void setDeliveryInstructions(String deliveryInstructions) { this.deliveryInstructions = deliveryInstructions; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public BigDecimal getOrderPrice() { return orderPrice; }

    public void setOrderPrice(BigDecimal orderPrice) { this.orderPrice = orderPrice; }

    public BigDecimal getDeliveryCharges() { return deliveryCharges; }

    public void setDeliveryCharges(BigDecimal deliveryCharges) { this.deliveryCharges = deliveryCharges; }

    public BigDecimal getPackagingFees() { return packagingFees; }

    public void setPackagingFees(BigDecimal packagingFees) { this.packagingFees = packagingFees; }

    public BigDecimal getTax() { return tax; }

    public void setTax(BigDecimal tax) { this.tax = tax; }

    public BigDecimal getPlatformFees() { return platformFees; }

    public void setPlatformFees(BigDecimal platformFees) { this.platformFees = platformFees; }

    public DelivaryPartner getDeliveryPartner() { return deliveryPartner; }

    public void setDeliveryPartner(DelivaryPartner deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public Restaurant getRestaurant() { return restaurant; }

    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public Payment getPayment() { return payment; }

    public void setPayment(Payment payment) { this.payment = payment; }

    public List<Item> getItems() { return items; }

    public void setItems(List<Item> items) { this.items = items; }
}