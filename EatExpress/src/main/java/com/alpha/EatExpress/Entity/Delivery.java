package com.alpha.EatExpress.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deliveryStatus; // ASSIGNED, OUT_FOR_DELIVERY, DELIVERED
    private String deliveryAgentName;
    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Delivery() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDeliveryStatus() { return deliveryStatus; }
    public void setDeliveryStatus(String deliveryStatus) { this.deliveryStatus = deliveryStatus; }

    public String getDeliveryAgentName() { return deliveryAgentName; }
    public void setDeliveryAgentName(String deliveryAgentName) { this.deliveryAgentName = deliveryAgentName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}
