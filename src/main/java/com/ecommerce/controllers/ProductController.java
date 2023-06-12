package com.ecommerce.controllers;

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
        listProducts();
        break;
      case 2:
        createProduct();
        break;
      case 3:
        removeProduct();
        break;
    }
  }

  public void listProducts() {
    User user = UserController.loggedUser;
    if (user != null) {
      for (Product p : user.getProducts()) {
        System.out.println(p.toString());
      }
    }
  }

  public void createProduct() {
    User user = UserController.loggedUser;
    if (user != null) {
      String name = ScannerUtils.nextLine("Nome do produto: ");
      String description = ScannerUtils.nextLineln("Descrição do produto: ");
      double price = ScannerUtils.nextDouble("Preço do produto: (Ex.: 0,00) ");
      int quantity = ScannerUtils.nextInt("Unidades: ");
      productDAO.create(new Product(user, name, description, price, quantity));
    }
  }

  public void removeProduct() {

  }
}
