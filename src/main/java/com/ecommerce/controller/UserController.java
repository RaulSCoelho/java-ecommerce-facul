package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;
import com.ecommerce.models.UserType;
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

  public User createUser(boolean askUserType) {
    UserType userType = UserType.CLIENT;

    if (askUserType) {
      System.out.println("Escolha o tipo de usuário:");
      System.out.println("1 - Admin");
      System.out.println("2 - Cliente");
      int type = ScannerUtils.nextInt();
      if (type == 1)
        userType = UserType.ADMIN;
    }

    String name = ScannerUtils.nextLine("Nome: ");
    String username = ScannerUtils.nextLine("Username: ");
    String email = ScannerUtils.nextLine("Email: ");
    String address = ScannerUtils.nextLine("Endereço: ");
    String password;
    String confirmPassword;

    do {
      password = ScannerUtils.nextLine("Senha: ");
      confirmPassword = ScannerUtils.nextLine("Confirme a senha: ");

      if (!password.equals(confirmPassword)) {
        System.out.println("Senhas diferentes. Por favor, tente novamente.");
      }
    } while (!password.equals(confirmPassword));

    User user = new User(userType, name, username, email, address, password);
    userDAO.create(user);
    return user;
  }
}
