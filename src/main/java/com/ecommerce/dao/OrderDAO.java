package com.ecommerce.dao;

import com.ecommerce.models.Order;

public class OrderDAO extends GenericDAO<Order> {

  public OrderDAO() {
    super(Order.class);
  }

}
