package com.ecommerce.utils;

public class TerminalUtils {
  private static final String WHITE = "\u001B[0m";
  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String BLUE = "\u001B[34m";
  private static final String YELLOW = "\u001B[33m";

  private static void print(String message, String color) {
    System.out.print(color + message + WHITE);
  }

  private static void println(String message, String color) {
    System.out.println(color + message + WHITE);
  }

  public static void success(String message) {
    print(message, GREEN);
  }

  public static void successln(String message) {
    println(message, GREEN);
  }

  public static void warning(String message) {
    print(message, RED);
  }

  public static void warningln(String message) {
    println(message, RED);
  }

  public static void alert(String message) {
    print(message, YELLOW);
  }

  public static void alertln(String message) {
    println(message, YELLOW);
  }

  public static void info(String message) {
    print(message, BLUE);
  }

  public static void infoln(String message) {
    println(message, BLUE);
  }
}