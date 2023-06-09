package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;

public class AuthController {
  private static UserDAO userDAO = new UserDAO();

  public User login(String username, String password) {
    User user = userDAO.findByUsername(username);

    if (user == null)
      throw new Error("Nome de usuário ou senha inválidos!");

    return user;
  }
}
