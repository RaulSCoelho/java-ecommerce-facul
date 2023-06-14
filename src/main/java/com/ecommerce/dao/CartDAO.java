package com.ecommerce.dao;

import com.ecommerce.models.Cart;

public class CartDAO extends GenericDAO<Cart> {

  public CartDAO() {
    super(Cart.class);
  }

  public Cart getCartByUserId(Long id) {
    return findOneByColumn("owner", id);
  }
}
