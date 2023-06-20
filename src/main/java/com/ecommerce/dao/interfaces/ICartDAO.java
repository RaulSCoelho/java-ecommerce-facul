package com.ecommerce.dao.interfaces;

import com.ecommerce.models.Cart;

public interface ICartDAO extends IGenericDAO<Cart> {
  Cart getCartByUserId(Long id);
}
