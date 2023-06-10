package com.ecommerce;

import com.ecommerce.controller.UserController;
import com.ecommerce.models.User;

public class App {
    private static UserController userController = new UserController();

    public static void main(String[] args) {
        User user = userController.login("RaulSCoelho", "teste123");
        if (user != null)
            System.out.println(user.toString());
    }
}
