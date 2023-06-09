package com.ecommerce.utils;

import java.util.Scanner;

public class ScannerUtils {
  private static Scanner scanner = new Scanner(System.in);

  public static String nextLineln(String message) {
    System.out.println(message);
    return nextLine();
  }

  public static String nextLine(String message) {
    System.out.print(message);
    return nextLine();
  }

  public static String nextLine() {
    return scanner.nextLine();
  }

  public static int nextIntln(String message) {
    System.out.println(message);
    return nextInt();
  }

  public static int nextInt(String message) {
    System.out.print(message);
    return nextInt();
  }

  public static int nextInt() {
    try {
      int number = scanner.nextInt();
      scanner.nextLine();
      return number;
    } catch (Exception ex) {
      scanner.nextLine();
      return nextInt("Você deve digitar um número inteiro: ");
    }
  }

  public static double nextDoubleln(String message) {
    System.out.println(message);
    return nextDouble();
  }

  public static double nextDouble(String message) {
    System.out.print(message);
    return nextDouble();
  }

  public static double nextDouble() {
    try {
      double number = scanner.nextDouble();
      scanner.nextLine();
      return number;
    } catch (Exception ex) {
      scanner.nextLine();
      return nextDouble("Você deve digitar um número inteiro: ");
    }
  }
}
