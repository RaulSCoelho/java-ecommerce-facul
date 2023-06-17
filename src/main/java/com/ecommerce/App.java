package com.ecommerce;

import com.ecommerce.controllers.ProductController;
import com.ecommerce.controllers.ProfileController;
import com.ecommerce.controllers.ShopController;
import com.ecommerce.controllers.UserController;
import com.ecommerce.models.User;
import com.ecommerce.utils.ScannerUtils;
import com.ecommerce.utils.TerminalUtils;

public class App {
  private static UserController userController = new UserController();
  private static ShopController shopController = new ShopController();
  private static ProductController productController = new ProductController();
  private static ProfileController profileController = new ProfileController();

  public static void main(String[] args) {
    startProgram();
  }

  public static void startProgram() {
    User user = UserController.loggedUser;
    boolean isLogged = user != null;
    boolean isAdmin = isLogged && user.isAdmin();

    int action = chooseAction(isLogged, isAdmin);
    if (action == 0)
      return;

    System.out.println();

    try {
      doAction(action, isLogged, isAdmin);
    } catch (Throwable ex) {
      TerminalUtils.warningln(ex.getMessage());
    }

    System.out.println();
    startProgram();
  }

  public static void doAction(int action, boolean isLogged, boolean isAdmin) {
    if (!isLogged) {
      switch (action) {
        case 1:
          UserController.login();
          break;
        case 2:
          userController.createUser(false);
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
        case 3:
          userController.deleteUser();
          break;
        case 4:
          UserController.logout();
          break;
      }
    } else {
      switch (action) {
        case 1:
          shopController.listProducts();
          break;
        case 2:
          productController.menu();
          break;
        case 3:
          // CARRINHO
          break;
        case 4:
          profileController.menu();
          break;
        case 5:
          UserController.logout();
          break;
      }
    }
  }

  public static int chooseAction(boolean isLogged, boolean isAdmin) {
    TerminalUtils.infoln("Escolha uma ação: ");
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
      System.out.println("3 - Ir para o carrinho");
      System.out.println("4 - Ir para meu perfil");
      System.out.println("5 - Logout");
    }
    System.out.println("0 - Finalizar programa");

    return ScannerUtils.nextInt();
  }
}
