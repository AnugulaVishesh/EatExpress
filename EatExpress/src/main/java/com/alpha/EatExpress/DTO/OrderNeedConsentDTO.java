package com.alpha.EatExpress.DTO;

import java.math.BigDecimal;
import java.util.List;

public class OrderNeedConsentDTO {

    private int orderId;
    private String customerName;
    private String deliveryAddress;

    private BigDecimal orderPrice;
    private BigDecimal deliveryCharges;
    private BigDecimal packagingFees;
    private BigDecimal platformFees;
    private BigDecimal tax;
    private BigDecimal totalCost;

    private double distance;

    private List<String> items;

    // getters and setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(BigDecimal deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public BigDecimal getPackagingFees() {
        return packagingFees;
    }

    public void setPackagingFees(BigDecimal packagingFees) {
        this.packagingFees = packagingFees;
    }

    public BigDecimal getPlatformFees() {
        return platformFees;
    }

    public void setPlatformFees(BigDecimal platformFees) {
        this.platformFees = platformFees;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}