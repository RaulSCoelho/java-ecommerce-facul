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

    products = products.stream().filter(p -> p.getQuantity() > 0).toList();

    if (products.size() == 0) {
      TerminalUtils.warningln("Não há produtos à venda!");
      return;
    }

    TerminalUtils.clearConsole();

    while (!exit) {
      Product product = products.get(currentIndex);
      System.out.println();
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

  private void addToCart(Product product) {
    Cart cart = UserController.loggedUser.getCart();
    if (cart != null) {
      List<Product> products = cart.getProducts();
      List<Product> sameProducts = products.stream().filter(p -> p.getId().equals(product.getId())).toList();

      if (sameProducts.size() >= product.getQuantity()) {
        throw new Error("Item fora de estoque!");
      }

      cart.addProduct(product);
      cartDAO.update(cart);
    } else {
      cart = new Cart(UserController.loggedUser, product);
      cartDAO.create(cart);
    }
    UserController.reloadUser();
  }

  public void checkout() {
    Cart cart = UserController.loggedUser.getCart();

    if (cart == null) {
      throw new Error("Carrinho vazio!");
    }

    List<Product> products = cart.getProducts();
    List<Product> outOfStock = products.stream().filter(p -> p.getQuantity() == 0).toList();

    if (outOfStock.size() > 0) {
      cart.removeProducts(outOfStock);
      cartDAO.update(cart);
    }

    TerminalUtils.infoln("Produtos:");
    for (Product p : products) {
      TerminalUtils.alert(p.getName() + ": ");
      System.out.println(TerminalUtils.money(p.getPrice()));
    }

    double totalAmount = products.stream().mapToDouble(Product::getPrice).reduce(0, Double::sum);
    TerminalUtils.alert("Total: ");
    System.out.println(TerminalUtils.money(totalAmount));

    boolean accepeted = TerminalUtils.yesOrNo("Finalizar compra? (s/n) ");

    if (accepeted) {

      UserController.reloadUser();
    }
  }
}