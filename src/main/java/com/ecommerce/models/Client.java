package com.ecommerce.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("client")
public class Client extends User {
  @OneToMany(mappedBy = "client")
  private List<PurchaseOrder> orders;

  public Client(String name, String username, String password, String email, String address) {
    super(name, username, password, email, address);
  }

  public List<PurchaseOrder> getOrders() {
    return orders;
  }

  public void addOrder(PurchaseOrder order) {
    orders.add(order);
  }
}
