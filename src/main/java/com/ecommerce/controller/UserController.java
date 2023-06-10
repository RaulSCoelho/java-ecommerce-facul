package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;
import com.ecommerce.utils.ScannerUtils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserController {
  private static UserDAO userDAO = new UserDAO();

  public User login() {
    String username = ScannerUtils.nextLine("Username: ");
    String password = ScannerUtils.nextLine("Password: ");

    User user = userDAO.findByUsername(username);

    if (user == null)
      throw new Error("Nome de usuário ou senha inválidos!");

    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

    if (!result.verified)
      throw new Error("Nome de usuário ou senha inválidos!");

    return user;
  }

  public void createUser(User user) {
    userDAO.create(user);
  }
}