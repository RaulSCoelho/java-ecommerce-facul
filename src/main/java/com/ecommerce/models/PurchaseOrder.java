package com.ecommerce.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class PurchaseOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private User client;

  @ManyToMany
  @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products;

  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @Column(name = "total_amount")
  private double totalAmount;

  public PurchaseOrder(User client, List<Product> products, double totalAmount) {
    this.client = client;
    this.products = products;
    this.totalAmount = totalAmount;
    this.orderDate = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public User getClient() {
    return client;
  }

  public List<Product> getProducts() {
    return products;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public double getTotalAmount() {
    return totalAmount;
  }
}