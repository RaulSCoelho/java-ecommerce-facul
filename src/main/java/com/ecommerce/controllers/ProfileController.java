package com.ecommerce.controllers;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;
import com.ecommerce.utils.FileUtils;
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
    System.out.println();

    int action = ScannerUtils.nextInt();

    switch (action) {
      case 1:
        profile();
        break;
      case 2:
        depositMoney();
        break;
      case 3:
        deleteAccount();
        break;
      default:
        return;
    }

    System.out.println();
    menu();
  }

  private void profile() {
    TerminalUtils.infoln("\nEscolha uma ação: ");
    System.out.println("1 - Alterar nome");
    System.out.println("2 - Alterar email");
    System.out.println("3 - Alterar username");
    System.out.println("4 - Alterar endereço");
    System.out.println("5 - Alterar minha senha");
    System.out.println("6 - Voltar");

    int action = ScannerUtils.nextInt();
    User user = UserController.loggedUser;
    System.out.println();

    switch (action) {
      case 1:
        String newName = ScannerUtils.nextLine("Novo nome: ");
        user.setName(newName);
        break;
      case 2:
        String newEmail = ScannerUtils.nextLine("Novo email: ");
        user.setEmail(newEmail);
        break;
      case 3:
        String newUsername = ScannerUtils.nextLine("Novo username: ");
        user.setUsername(newUsername);
        break;
      case 4:
        String newAddress = ScannerUtils.nextLine("Novo endereço: ");
        user.setAddress(newAddress);
        break;
      case 5:
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
    TerminalUtils.successln("Alterações salvas com sucesso!\n");
  }

  private void depositMoney() {
    User user = UserController.loggedUser;

    TerminalUtils.info("\nQuanto você quer depositar? ");
    Double amount = ScannerUtils.nextDouble();

    user.depositMoney(amount);
    userDAO.update(user);
    UserController.reloadUser();
    TerminalUtils.successln("Depositado com sucesso!\n");
  }

  private void deleteAccount() {
    User user = UserController.loggedUser;
    boolean accepeted = TerminalUtils.yesOrNo("Tem certeza que deseja remover? (s/n) ");

    if (accepeted) {
      userDAO.delete(user);
      FileUtils.removeFile("user.obj");
      TerminalUtils.successln("Conta removida com sucesso!");
    }
  }
}