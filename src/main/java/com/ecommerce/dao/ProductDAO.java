package com.ecommerce.dao;

import com.ecommerce.models.Product;

public class ProductDAO extends GenericDAO<Product> {

  public ProductDAO() {
    super(Product.class);
  }

}
