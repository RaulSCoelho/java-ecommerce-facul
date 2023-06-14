package com.ecommerce.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
      List<Product> products = productDAO.getProductsByUserId(user.getId());

      if (products.size() == 0) {
        TerminalUtils.warningln("Sem produtos para mostrar!");
      }

      for (int i = 0; i < products.size(); i++) {
        Product product = products.get(i);

        TerminalUtils.alert("Nome: ");
        System.out.println(product.getName());
        TerminalUtils.alert("Descrição: ");
        System.out.println(product.getDescription());
        TerminalUtils.alert("Preço: ");
        System.out.println(String.format("R$ %.2f", product.getPrice()));
        TerminalUtils.alert("Quantidade em estoque: ");
        System.out.println(product.getQuantity());

        if (i < products.size() - 1) {
          System.out.println();
        }
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
    User user = UserController.loggedUser;
    if (user != null) {
      List<Product> products = productDAO.getProductsByUserId(user.getId());

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
      TerminalUtils.info(String.format("Tem certeza que deseja remover %s? (s/n) ", productToRemove.getName()));
      String response = ScannerUtils.nextLine();
      Set<String> acceptedResponses = new HashSet<>(Arrays.asList("s", "sim", "y", "yes"));

      if (acceptedResponses.contains(response.toLowerCase())) {
        productDAO.delete(productToRemove);
      }

      TerminalUtils.successln("Produto removido com sucesso!");
    }
  }
}
