package com.ecommerce.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User owner;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products = new ArrayList<>();

  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @Column(name = "total_amount")
  private double totalAmount;

  public PurchaseOrder() {
  }

  public PurchaseOrder(User owner, List<Product> products, double totalAmount) {
    this.owner = owner;
    this.products = products;
    this.totalAmount = totalAmount;
    this.orderDate = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public User getOwner() {
    return owner;
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