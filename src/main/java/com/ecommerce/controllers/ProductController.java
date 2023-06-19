package com.ecommerce.controllers;

import java.util.List;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Product;
import com.ecommerce.models.User;
import com.ecommerce.utils.ScannerUtils;
import com.ecommerce.utils.TerminalUtils;

public class ProductController {
  private static ProductDAO productDAO = new ProductDAO();

  public void menu() {
    TerminalUtils.infoln("Escolha uma ação: ");
    System.out.println("1 - Listar produtos");
    System.out.println("2 - Criar um produto");
    System.out.println("3 - Remover um produto");
    System.out.println("4 - Voltar");

    int action = ScannerUtils.nextInt();

    switch (action) {
      case 1:
        System.out.println();
        listProducts();
        break;
      case 2:
        System.out.println();
        createProduct();
        break;
      case 3:
        System.out.println();
        removeProduct();
        break;
      default:
        return;
    }

    System.out.println();
    menu();
  }

  private void listProducts() {
    User user = UserController.loggedUser;
    if (user != null) {
      List<Product> products = user.getProducts();
      int currentIndex = 0;
      boolean exit = false;

      if (products == null || products.size() == 0) {
        TerminalUtils.warningln("Sem produtos para mostrar!");
        return;
      }

      TerminalUtils.clearConsole();

      while (!exit) {
        Product product = products.get(currentIndex);
        System.out.println();
        product.print();
        TerminalUtils.infoln(String.format("Produto %d de %d", currentIndex + 1, products.size()));

        String input = ScannerUtils
            .nextLine("\nPressione 'p' para próximo produto, 'a' para produto anterior ou 's' para sair: ");

        switch (input.trim().toLowerCase()) {
          case "p":
            if (currentIndex < products.size() - 1) {
              currentIndex++;
            }
            break;
          case "a":
            if (currentIndex > 0) {
              currentIndex--;
            }
            break;
          default:
            exit = true;
        }
        TerminalUtils.clearConsole();
      }
    }
  }

  private void createProduct() {
    User user = UserController.loggedUser;
    if (user != null) {
      String name = ScannerUtils.nextLine("Nome do produto: ");
      String description = ScannerUtils.nextLineln("Descrição do produto: ");
      double price = ScannerUtils.nextDouble("Preço do produto: (Ex.: 0,00) ");
      int quantity = ScannerUtils.nextInt("Unidades: ");

      productDAO.create(new Product(user, name, description, price, quantity));
      UserController.reloadUser();
      TerminalUtils.successln("Produto criado com sucesso!");
    }
  }

  private void removeProduct() {
    User user = UserController.loggedUser;
    if (user != null) {
      List<Product> products = user.getProducts();

      if (products == null || products.size() == 0) {
        TerminalUtils.warningln("Sem produtos para remover!");
        return;
      }

      TerminalUtils.infoln("Qual produto deseja remover?");
      for (Product p : products) {
        int index = products.indexOf(p);
        System.out.println(String.format("%d - %s (%d)", index + 1, p.getName(), p.getQuantity()));
      }

      int indexToRemove = ScannerUtils.nextInt() - 1;

      if (indexToRemove < 0 || indexToRemove > products.size() - 1) {
        throw new Error("Produto inválido!");
      }

      Product productToRemove = products.get(indexToRemove);
      boolean accepeted = TerminalUtils
          .yesOrNo(String.format("Tem certeza que deseja remover %s? (s/n) ", productToRemove.getName()));

      if (accepeted) {
        productDAO.delete(productToRemove);
      }

      UserController.reloadUser();
      TerminalUtils.successln("Produto removido com sucesso!");
    }
  }
}