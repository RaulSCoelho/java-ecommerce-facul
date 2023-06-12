package com.ecommerce.controllers;

import java.util.List;
import java.util.Scanner;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Product;

public class ShopController {
  private static ProductDAO productDAO = new ProductDAO();
  private List<Product> products = productDAO.findAll();
  private int currentIndex = 0;
  private Scanner scanner = new Scanner(System.in);

  public void listProducts() {
    boolean exit = false;
    clearConsole();

    while (!exit) {
      displayProduct(products.get(currentIndex));
      System.out.println("\nPress 'n' for next product, 'p' for previous product, or 'q' to quit:");

      String input = scanner.nextLine().trim().toLowerCase();
      switch (input) {
        case "n":
          nextProduct();
          break;
        case "p":
          previousProduct();
          break;
        case "q":
          exit = true;
          break;
        default:
          System.out.println("Invalid input. Please try again.");
      }
      clearConsole();
    }
  }

  private void displayProduct(Product product) {
    System.out.println("Product ID: " + product.getId());
    System.out.println("Product Name: " + product.getName());
    System.out.println("Product Price: " + product.getPrice());
    // Add any other product details you want to display
  }

  private void nextProduct() {
    if (currentIndex < products.size() - 1) {
      currentIndex++;
    }
  }

  private void previousProduct() {
    if (currentIndex > 0) {
      currentIndex--;
    }
  }

  private void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}