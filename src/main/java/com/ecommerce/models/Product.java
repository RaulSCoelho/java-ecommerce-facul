package com.ecommerce.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private double price;
  private String description;
  private int quantity;

  public Product(String name, double price, String description, int quantity) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public String getDescription() {
    return description;
  }

  public int getQuantity() {
    return quantity;
  }
}
