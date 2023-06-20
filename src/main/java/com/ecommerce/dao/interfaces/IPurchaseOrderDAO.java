package com.ecommerce.dao.interfaces;

import java.util.List;

import com.ecommerce.models.PurchaseOrder;

public interface IPurchaseOrderDAO extends IGenericDAO<PurchaseOrder> {
  List<PurchaseOrder> getOrdersByUserId(Long id);
}
