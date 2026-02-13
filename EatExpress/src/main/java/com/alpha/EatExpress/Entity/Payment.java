package com.alpha.EatExpress.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method; // UPI, CARD, COD
    private String status; // SUCCESS, FAILED
    private double amount;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Payment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
