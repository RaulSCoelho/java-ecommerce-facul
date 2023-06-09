package com.ecommerce.dao;

import com.ecommerce.models.User;

public class UserDAO extends GenericDAO<User> {

  public UserDAO() {
    super(User.class);
  }

  public User findByUsername(String username) {
    return findOneByColumn("username", username);
  }
}
