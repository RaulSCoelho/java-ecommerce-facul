package com.ecommerce;

import com.ecommerce.dao.ClientDAO;
import com.ecommerce.dao.PurchaseOrderDAO;
import com.ecommerce.dao.ProductDAO;

public class App {
    private static ClientDAO clientDAO = new ClientDAO();
    private static ProductDAO productDAO = new ProductDAO();
    private static PurchaseOrderDAO orderDAO = new PurchaseOrderDAO();

    public static void main(String[] args) {
    }
}
