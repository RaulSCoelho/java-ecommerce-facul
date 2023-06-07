package com.ecommerce.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToMany
  @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products;

  private LocalDateTime orderDate;
  private double totalAmount;

  public Order(double totalAmount, Client client) {
    this.totalAmount = totalAmount;
    this.client = client;
    this.orderDate = LocalDateTime.now();
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
