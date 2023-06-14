package com.ecommerce.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.ecommerce.utils.TerminalUtils;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Entity
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_type")
  private String userType;

  @OneToOne(mappedBy = "owner", fetch = FetchType.EAGER)
  private Cart cart;

  @OneToMany(mappedBy = "owner")
  private List<Product> products;

  @OneToMany(mappedBy = "owner")
  private List<PurchaseOrder> orders;

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

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public List<PurchaseOrder> getOrders() {
    return orders;
  }

  public void setOrders(List<PurchaseOrder> orders) {
    this.orders = orders;
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

  public void print() {
    TerminalUtils.alertln(String.format("%s - %s", name, userType));
    TerminalUtils.alert("username: ");
    System.out.println(username);
    TerminalUtils.alert("email: ");
    System.out.println(email);
    TerminalUtils.alert("endereço: ");
    System.out.println(address);
    TerminalUtils.alert("saldo: ");
    System.out.println(String.format("R$ %.2f", balance));
  }
}