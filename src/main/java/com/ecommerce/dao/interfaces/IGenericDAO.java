package com.ecommerce.dao.interfaces;

import java.util.List;

public interface IGenericDAO<T> {
  List<T> findAll();

  T findById(Long id);

  <TT> List<TT> findByColumn(Class<TT> entityClass, String columnName, Object value);

  List<T> findByColumn(String columnName, Object value);

  T findOneByColumn(String columnName, Object value);

  void create(T entity);

  void update(T entity);

  void delete(T entity);
}
