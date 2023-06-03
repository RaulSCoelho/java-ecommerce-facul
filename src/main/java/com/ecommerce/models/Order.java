package com.ecommerce.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Client client;

  private LocalDateTime orderDate;
  private double totalAmount;

  public Order(LocalDateTime orderDate, double totalAmount, Client client) {
    this.orderDate = orderDate;
    this.totalAmount = totalAmount;
    this.client = client;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public double getTotalAmount() {
    return totalAmount;
  }
}
