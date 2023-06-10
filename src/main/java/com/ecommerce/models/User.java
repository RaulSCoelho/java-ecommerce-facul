package com.ecommerce.models;

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

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PurchaseOrder> orders;

  private String name;
  private String username;
  private String password;
  private String email;
  private String address;
  private Double balance = 0.0;

  public User() {
  }

  public User(UserType userType, String name, String username, String password, String email, String address) {
    this.userType = userType.toString();
    this.name = name;
    this.username = username;
    this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    this.email = email;
    this.address = address;
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

  public boolean isAdmin() {
    return userType == UserType.ADMIN.toString();
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
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