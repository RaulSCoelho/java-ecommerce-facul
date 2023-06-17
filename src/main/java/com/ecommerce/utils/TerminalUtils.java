package com.ecommerce.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TerminalUtils {
  private static final String RESET = "\u001B[0m";
  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String BLUE = "\u001B[34m";
  private static final String YELLOW = "\u001B[33m";

  private static void print(String message, String color) {
    System.out.print(color + message + RESET);
  }

  private static void println(String message, String color) {
    System.out.println(color + message + RESET);
  }

  public static boolean yesOrNo(String message) {
    TerminalUtils.info(message);
    String response = ScannerUtils.nextLine();
    Set<String> acceptedResponses = new HashSet<>(Arrays.asList("s", "sim", "y", "yes"));
    return acceptedResponses.contains(response.toLowerCase());
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

  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}