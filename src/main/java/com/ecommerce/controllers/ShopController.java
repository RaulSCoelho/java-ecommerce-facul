package com.ecommerce.controllers;

import java.util.List;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.dao.PurchaseOrderDAO;
import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.Cart;
import com.ecommerce.models.Product;
import com.ecommerce.models.PurchaseOrder;
import com.ecommerce.models.User;
import com.ecommerce.utils.ScannerUtils;
import com.ecommerce.utils.TerminalUtils;

public class ShopController {
  private static ProductDAO productDAO = new ProductDAO();
  private static CartDAO cartDAO = new CartDAO();
  private static PurchaseOrderDAO orderDAO = new PurchaseOrderDAO();
  private static UserDAO userDAO = new UserDAO();

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

  public void removeFromCart() {
    Cart cart = UserController.loggedUser.getCart();

    if (cart == null) {
      throw new Error("Carrinho vazio!");
    }

    List<Product> products = cart.getProducts();
    List<Product> outOfStock = products.stream().filter(p -> p.getQuantity() == 0).toList();

    if (outOfStock.size() > 0) {
      cart.removeProducts(outOfStock);
      cartDAO.update(cart);
      UserController.reloadUser();
      cart = UserController.loggedUser.getCart();
      products = cart.getProducts();
    }

    if (products.size() == 0) {
      throw new Error("Carrinho vazio!");
    }

    TerminalUtils.infoln("Qual produto deseja remover?");
    for (Product p : products) {
      int index = products.indexOf(p);
      System.out.println(String.format("%d - %s (R$ %.2f)", index + 1, p.getName(), p.getPrice()));
    }

    int indexToRemove = ScannerUtils.nextInt() - 1;

    if (indexToRemove < 0 || indexToRemove > products.size() - 1) {
      throw new Error("Produto inválido!");
    }

    Product productToRemove = products.get(indexToRemove);
    boolean accepeted = TerminalUtils
        .yesOrNo(String.format("Tem certeza que deseja remover %s do carrinho? (s/n) ", productToRemove.getName()));

    if (accepeted) {
      cart.removeProduct(productToRemove);
      cartDAO.update(cart);
      UserController.reloadUser();
      TerminalUtils.successln("Produto removido com sucesso!");
    }
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
      UserController.reloadUser();
      cart = UserController.loggedUser.getCart();
      products = cart.getProducts();
    }

    if (products.size() == 0) {
      throw new Error("Carrinho vazio!");
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
      User user = UserController.loggedUser;
      user.withdrawMoney(totalAmount);
      userDAO.update(user);

      for (Product product : products) {
        int amount = products.stream().filter(p -> p.getId().equals(product.getId())).toList().size();
        product.setQuantity(product.getQuantity() - amount);
        productDAO.update(product);
      }

      PurchaseOrder order = new PurchaseOrder(user, products, totalAmount);
      orderDAO.create(order);

      cart.clearCart();
      cartDAO.update(cart);
      UserController.reloadUser();
      TerminalUtils.successln("Compra concluída com sucesso!");
    }
  }
}