package com.ecommerce.dao;

import com.ecommerce.models.User;

public class UserDAO extends GenericDAO<User> {

  public UserDAO() {
    super(User.class);
  }

  @Override
  public void create(User user) {
    if (findByUsername(user.getUsername()) != null) {
      throw new Error("Já exite um usuário com esse username.");
    } else if (findByEmail(user.getEmail()) != null) {
      throw new Error("Já exite um usuário com esse email.");
    }

    super.create(user);
  }

  public User findByUsername(String username) {
    User user = findOneByColumn("username", username);
    return user;
  }

  public User findByEmail(String email) {
    User user = findOneByColumn("email", email);
    return user;
  }
}
