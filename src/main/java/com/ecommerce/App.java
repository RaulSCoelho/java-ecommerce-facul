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
        boolean isLogged = user != null;
        boolean isAdmin = isLogged && user.isAdmin();

        int action = chooseAction(isLogged, isAdmin);
        if (action == 0)
            return;

        System.out.println();

        try {
            doAction(action, isLogged, isAdmin);
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println();
        startProgram();
    }

    public static void doAction(int action, boolean isLogged, boolean isAdmin) {
        switch (action) {
            case 1:
                if (isLogged)
                    user = null;
                else
                    user = userController.login();
                break;
        }
    }

    public static int chooseAction(boolean isLogged, boolean isAdmin) {
        if (!isLogged) {
            System.out.println("Escolha uma ação: ");
            System.out.println("1 - Login");
            System.out.println("0 - Finalizar o programa");
        } else if (isAdmin) {
            System.out.println("Escolha uma ação: ");
            System.out.println("1 - Criar um usuário");
            System.out.println("2 - Remover um usuário");
            System.out.println("3 - Adicionar um produto");
            System.out.println("1 - Logout");
            System.out.println("0 - Finalizar programa");
        } else {
            System.out.println("Escolha uma ação: ");
            System.out.println("1 - Logout");
            System.out.println("0 - Finalizar programa");
        }

        return ScannerUtils.nextInt();
    }
}
