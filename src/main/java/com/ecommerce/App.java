package com.ecommerce;

import com.ecommerce.controller.UserController;
import com.ecommerce.models.User;
import com.ecommerce.utils.ScannerUtils;

public class App {
  private static UserController userController = new UserController();
  private static User user;

  public static void main(String[] args) {
    startProgram();
  }

  public static void startProgram() {
    boolean isLogged = user != null;
    boolean isAdmin = isLogged && user.isAdmin();

    int action = chooseAction(isLogged, isAdmin);
    if (action == 0)
      return;

    System.out.println();

    try {
      doAction(action, isLogged, isAdmin);
    } catch (Throwable ex) {
      System.out.println(ex.getMessage());
    }

    System.out.println();
    startProgram();
  }

  public static void doAction(int action, boolean isLogged, boolean isAdmin) {
    if (!isLogged) {
      switch (action) {
        case 1:
          user = userController.login();
          break;
        case 2:
          user = userController.createUser(false);
          break;
      }
    } else if (isAdmin) {
      switch (action) {
        case 1:
          userController.listUsers();
          break;
        case 2:
          userController.createUser(true);
          break;
        case 4:
          user = null;
          break;
      }
    } else {
      switch (action) {
        case 4:
          user = null;
          break;
      }
    }
  }

  public static int chooseAction(boolean isLogged, boolean isAdmin) {
    System.out.println("Escolha uma ação: ");
    if (!isLogged) {
      System.out.println("1 - Login");
      System.out.println("2 - Criar uma conta");
    } else if (isAdmin) {
      System.out.println("1 - Listar usuários");
      System.out.println("2 - Criar um usuário");
      System.out.println("3 - Remover um usuário");
      System.out.println("4 - Logout");
    } else {
      System.out.println("1 - Ir para a loja");
      System.out.println("2 - Ir para meus produtos");
      System.out.println("3 - Ir para meu perfil");
      System.out.println("4 - Logout");
    }
    System.out.println("0 - Finalizar programa");

    return ScannerUtils.nextInt();
  }
}
