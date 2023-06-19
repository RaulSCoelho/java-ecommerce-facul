package com.ecommerce.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User owner;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products = new ArrayList<>();

  public Cart() {
  }

  public Cart(User owner, Product product) {
    this.owner = owner;
    products.add(product);
  }

  public Cart(User owner, List<Product> products) {
    this.owner = owner;
    products.addAll(products);
  }

  public User getOwner() {
    return owner;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void addProduct(Product product) {
    products.add(product);
  }

  public void removeProduct(Product product) {
    products.remove(product);
  }

  public void removeProducts(List<Product> products) {
    this.products.removeAll(products);
  }
}