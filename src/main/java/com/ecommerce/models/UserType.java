package com.ecommerce.models;

public enum UserType {
  ADMIN("admin"),
  CLIENT("client");

  private String value;

  private UserType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}