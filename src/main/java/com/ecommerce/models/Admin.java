package com.ecommerce.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

  public Admin(String name, String username, String password, String email, String address) {
    super(name, username, password, email, address);
  }

}
