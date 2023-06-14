package com.ecommerce.controllers;

import java.util.List;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Cart;
import com.ecommerce.models.Product;
import com.ecommerce.utils.ScannerUtils;
import com.ecommerce.utils.TerminalUtils;

public class ShopController {
  private static ProductDAO productDAO = new ProductDAO();
  private static CartDAO cartDAO = new CartDAO();

  public void listProducts() {
    List<Product> products = productDAO.findAll();
    int currentIndex = 0;
    boolean exit = false;

    if (products.size() == 0) {
      TerminalUtils.warningln("Não há produtos à venda!");
      return;
    }

    TerminalUtils.clearConsole();

    while (!exit) {
      Product product = products.get(currentIndex);
      product.print();
      TerminalUtils.infoln(String.format("Produto %d de %d", currentIndex + 1, products.size()));

      String input = ScannerUtils
          .nextLine(
              "\nPressione 'p' para próximo produto, 'a' para produto anterior, 'c' para adicionar ao carrinho ou 's' para sair: ");

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
        case "c":
          addToCart(product);
          break;
        default:
          exit = true;
      }
      TerminalUtils.clearConsole();
    }
  }

  public void addToCart(Product product) {
    Cart cart = UserController.loggedUser.getCart();
    if (cart != null) {
      cart.addProduct(product);
      cartDAO.update(cart);
    } else {
      cart = new Cart(UserController.loggedUser, product);
      cartDAO.create(cart);
    }
    UserController.reloadUser();
  }
}