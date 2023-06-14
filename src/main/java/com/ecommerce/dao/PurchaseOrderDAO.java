package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.models.PurchaseOrder;

public class PurchaseOrderDAO extends GenericDAO<PurchaseOrder> {

  public PurchaseOrderDAO() {
    super(PurchaseOrder.class);
  }

  public List<PurchaseOrder> getOrdersByUserId(Long id) {
    return findByColumn("owner", id);
  }
}
