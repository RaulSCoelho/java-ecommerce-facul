package com.ecommerce.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ecommerce.utils.TerminalUtils;

@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User owner;

  private String name;
  private String description;
  private double price;
  private int quantity;

  public Product() {
  }

  public Product(User owner, String name, String description, double price, int quantity) {
    this.owner = owner;
    this.name = name;
    this.description = description;
    this.price = price;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public User getOwner() {
    return owner;
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

  public void print() {
    TerminalUtils.alert("Nome: ");
    System.out.println(name);
    TerminalUtils.alert("Descrição: ");
    System.out.println(description);
    TerminalUtils.alert("Preço: ");
    System.out.println(String.format("R$ %.2f", price));
    TerminalUtils.alert("Quantidade em estoque: ");
    System.out.println(quantity);
  }
}