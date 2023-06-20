package com.ecommerce.controllers;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;
import com.ecommerce.utils.ScannerUtils;
import com.ecommerce.utils.TerminalUtils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ProfileController {
  private static UserDAO userDAO = new UserDAO();

  public void menu() {
    TerminalUtils.infoln("Escolha uma ação: ");
    System.out.println("1 - Meus dados");
    System.out.println("2 - Depositar dinheiro");
    System.out.println("3 - Remover conta");
    System.out.println("4 - Voltar");

    int action = ScannerUtils.nextInt();

    switch (action) {
      case 1:
        profile();
        break;
      case 2:
        System.out.println();
        depositMoney();
        break;
      case 3:
        System.out.println();
        deleteAccount();
        return;
      default:
        return;
    }

    System.out.println();
    menu();
  }

  private void profile() {
    System.out.println();
    UserController.loggedUser.print();

    TerminalUtils.infoln("\nEscolha uma ação: ");
    System.out.println("1 - Alterar nome");
    System.out.println("2 - Alterar email");
    System.out.println("3 - Alterar username");
    System.out.println("4 - Alterar endereço");
    System.out.println("5 - Alterar minha senha");
    System.out.println("6 - Voltar");

    int action = ScannerUtils.nextInt();
    User user = UserController.loggedUser;

    switch (action) {
      case 1:
        System.out.println();
        String newName = ScannerUtils.nextLine("Novo nome: ");
        user.setName(newName);
        break;
      case 2:
        System.out.println();
        String newEmail = ScannerUtils.nextLine("Novo email: ");
        user.setEmail(newEmail);
        break;
      case 3:
        System.out.println();
        String newUsername = ScannerUtils.nextLine("Novo username: ");
        user.setUsername(newUsername);
        break;
      case 4:
        System.out.println();
        String newAddress = ScannerUtils.nextLine("Novo endereço: ");
        user.setAddress(newAddress);
        break;
      case 5:
        System.out.println();
        String oldPassword;
        BCrypt.Result result;

        do {
          oldPassword = ScannerUtils.nextLine("Senha velha: ");
          result = BCrypt.verifyer().verify(oldPassword.toCharArray(), user.getPassword());

          if (!result.verified) {
            TerminalUtils.warningln("Senha incorreta, tente novamente.");
          }
        } while (!result.verified);

        String newPassword = ScannerUtils.nextLine("Nova senha: ");
        user.setPassword(newPassword);
        break;
      default:
        return;
    }

    userDAO.update(user);
    UserController.reloadUser();
    TerminalUtils.successln("Alterações salvas com sucesso!");
  }

  private void depositMoney() {
    User user = UserController.loggedUser;

    TerminalUtils.info("Quanto você quer depositar? ");
    Double amount = ScannerUtils.nextDouble();

    user.depositMoney(amount);
    userDAO.update(user);
    UserController.reloadUser();
    TerminalUtils.successln("Depositado com sucesso!");
  }

  private void deleteAccount() {
    User user = UserController.loggedUser;
    boolean accepeted = TerminalUtils.yesOrNo("Tem certeza que deseja remover? (s/n) ");

    if (accepeted) {
      userDAO.delete(user);
      UserController.logout();
      TerminalUtils.successln("Conta removida com sucesso!");
    }
  }
}