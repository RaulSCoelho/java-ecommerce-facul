package com.ecommerce;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;

public class App {
    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        User user = userDAO.findByUsername("RaulSCoelho");

        if (user != null)
            System.out.println(user.getUsername());
    }
}
