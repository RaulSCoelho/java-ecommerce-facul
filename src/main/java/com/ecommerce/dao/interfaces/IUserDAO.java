package com.ecommerce.dao.interfaces;

import com.ecommerce.models.User;

public interface IUserDAO extends IGenericDAO<User> {
  User findByUsername(String username);

  User findByEmail(String email);
}
