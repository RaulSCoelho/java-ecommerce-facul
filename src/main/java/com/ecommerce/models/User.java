package com.ecommerce.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_type")
  private String userType;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Product> products = new ArrayList<>();

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PurchaseOrder> orders = new ArrayList<>();

  private String name;
  private String username;
  private String email;
  private String address;
  private String password;
  private Double balance = 0.0;

  public User() {
  }

  public User(UserType userType, String name, String username, String email, String address, String password) {
    this.userType = userType.toString();
    this.name = name;
    this.username = username;
    this.email = email;
    this.address = address;
    this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
  }

  @Override
  public String toString() {
    return String.format("%s - %s\nusername: %s\nemail: %s\nendereço: %s\nsaldo: R$ %.2f", name, userType, username,
        email,
        address, balance);
  }

  public Long getId() {
    return id;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType.toString();
  }

  public List<PurchaseOrder> getOrders() {
    return orders;
  }

  public void addOrder(PurchaseOrder order) {
    orders.add(order);
  }

  public List<Product> getProducts() {
    return products;
  }

  public void addProduct(Product product) {
    products.add(product);
  }

  public boolean isAdmin() {
    return userType.equals(UserType.ADMIN.toString());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
  }

  public Double getBalance() {
    return balance;
  }

  public void depositMoney(Double amount) {
    balance += amount;
  }

  public void withdrawMoney(Double amount) {
    if (balance >= amount) {
      balance -= amount;
    } else {
      throw new Error("Saldo insuficiente.");
    }
  }
}