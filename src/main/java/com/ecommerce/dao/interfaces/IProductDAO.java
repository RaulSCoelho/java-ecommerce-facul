package com.ecommerce.dao.interfaces;

import java.util.List;

import com.ecommerce.models.Product;

public interface IProductDAO extends IGenericDAO<Product> {
  List<Product> getProductsByUserId(Long id);
}
