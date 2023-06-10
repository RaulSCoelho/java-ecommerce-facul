package com.ecommerce;

import com.ecommerce.controller.UserController;
import com.ecommerce.models.User;
import com.ecommerce.utils.ScannerUtils;

public class App {
    private static UserController userController = new UserController();
    private static User user;

    public static void main(String[] args) {
        startProgram();
    }

    public static void startProgram() {
        if (user == null) {
            System.out.println("Escolha uma ação: ");
            System.out.println("1 - Login");
            System.out.println("0 - Finalizar o programa");
        } else {
            System.out.println("Escolha uma ação: ");
            System.out.println("1 - Logout");
            System.out.println("0 - Finalizar programa");
        }

        int action = ScannerUtils.nextInt();

        if (action == 0)
            return;

        System.out.println();

        try {
            chooseAction(action);
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println();
        startProgram();
    }

    public static void chooseAction(int action) {
        boolean isLogged = user != null;

        switch (action) {
            case 1:
                if (isLogged)
                    user = null;
                else
                    user = userController.login();
                break;
        }
    }
}
