package com.ecommerce.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.dao.PurchaseOrderDAO;
import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.Cart;
import com.ecommerce.models.Product;
import com.ecommerce.models.PurchaseOrder;
import com.ecommerce.models.User;
import com.ecommerce.models.UserType;
import com.ecommerce.utils.FileUtils;
import com.ecommerce.utils.ScannerUtils;
import com.ecommerce.utils.TerminalUtils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserController {
  private static UserDAO userDAO = new UserDAO();
  private static ProductDAO productDAO = new ProductDAO();
  private static CartDAO cartDAO = new CartDAO();
  private static PurchaseOrderDAO orderDAO = new PurchaseOrderDAO();
  public static User loggedUser;

  public UserController() {
    User user = (User) FileUtils.readObject("user.obj");
    if (user != null) {
      loggedUser = user;
    }
  }

  public static void reloadUser() {
    if (loggedUser != null) {
      loggedUser = getUserInfos(userDAO.findById(loggedUser.getId()));
      FileUtils.storeObject(loggedUser, "user.obj");
    }
  }

  public static void login() {
    String username = ScannerUtils.nextLine("Username: ");
    String password = ScannerUtils.nextLine("Password: ");

    User user = userDAO.findByUsername(username);

    if (user == null) {
      throw new Error("Nome de usuário ou senha inválidos!");
    }

    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

    if (!result.verified) {
      throw new Error("Nome de usuário ou senha inválidos!");
    }

    loggedUser = getUserInfos(user);
    FileUtils.storeObject(user, "user.obj");
    TerminalUtils.successln("Logado com sucesso!");
  }

  public static void logout() {
    loggedUser = null;
    FileUtils.removeFile("user.obj");
  }

  public void listUsers() {
    List<User> users = userDAO.findAll();
    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);

      user.print();

      if (i < users.size() - 1) {
        System.out.println();
      }
    }
  }

  public void createUser(boolean askUserType) {
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
        TerminalUtils.warningln("Senhas diferentes. Por favor, tente novamente.");
      }
    } while (!password.equals(confirmPassword));

    User user = new User(userType, name, username, email, address, password);
    userDAO.create(user);

    loggedUser = getUserInfos(user);
    FileUtils.storeObject(user, "user.obj");
    TerminalUtils.successln("Usuário criado com sucesso!");
  }

  public void deleteUser() {
    List<User> users = userDAO.findAll();

    System.out.println("Qual usuário deseja remover?");
    for (User user : users) {
      int index = users.indexOf(user);
      System.out.println(String.format("%d - %s", index + 1, user.getUsername()));
    }

    int indexToRemove = ScannerUtils.nextInt() - 1;

    if (indexToRemove < 0 || indexToRemove > users.size() - 1) {
      throw new Error("Usuário inválido!");
    }

    User userToRemove = users.get(indexToRemove);
    TerminalUtils.info(String.format("Tem certeza que deseja remover %s? (s/n) ", userToRemove.getUsername()));
    String response = ScannerUtils.nextLine();
    Set<String> acceptedResponses = new HashSet<>(Arrays.asList("s", "sim", "y", "yes"));

    if (acceptedResponses.contains(response.toLowerCase())) {
      userDAO.delete(userToRemove);
      if (userToRemove.getId() == loggedUser.getId()) {
        logout();
      }
    }

    TerminalUtils.successln("Usuário removido com sucesso!");
  }

  private static User getUserInfos(User user) {
    Cart cart = cartDAO.getCartByUserId(user.getId());
    List<Product> products = productDAO.getProductsByUserId(user.getId());
    List<PurchaseOrder> orders = orderDAO.getOrdersByUserId(user.getId());
    user.setCart(cart);
    user.setProducts(products);
    user.setOrders(orders);
    return user;
  }
}
