package com.ecommerce.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Client extends User {
  @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
  private List<Order> orders;

  public Client(String name, String username, String password, String email, String address) {
    super(name, username, password, email, address);
  }

  public List<Order> getOrders() {
    return orders;
  }
}
