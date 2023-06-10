package com.ecommerce.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_type")
  private String userType;

  @OneToMany(mappedBy = "client")
  private List<PurchaseOrder> orders;

  private String name;
  private String username;
  private String password;
  private String email;
  private String address;

  public User() {
  }

  public User(UserType userType, String name, String username, String password, String email, String address) {
    this.userType = userType.toString();
    this.name = name;
    this.username = username;
    this.password = password;
    this.email = email;
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public String getUserType() {
    return userType;
  }

  public List<PurchaseOrder> getOrders() {
    return orders;
  }

  public void addOrder(PurchaseOrder order) {
    orders.add(order);
  }

  public void setUserType(UserType userType) {
    this.userType = userType.toString();
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }
}