package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.models.Product;

public class ProductDAO extends GenericDAO<Product> {

  public ProductDAO() {
    super(Product.class);
  }

  public List<Product> getProductsByUserId(Long id) {
    return findByColumn("owner", id);
  }

}
