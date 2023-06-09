package com.ecommerce.dao;

import com.ecommerce.models.Admin;

public class AdminDAO extends GenericDAO<Admin> {

  public AdminDAO() {
    super(Admin.class);
  }

  @Override
  public void create(Admin admin) {
    Admin existingAdmin = findById(admin.getId());

    if (existingAdmin.getUsername().equals(admin.getUsername())) {
      throw new Error("Já exite um usuário com esse username.");
    }

    if (existingAdmin.getEmail().equals(admin.getEmail())) {
      throw new Error("Já exite um usuário com esse email.");
    }

    super.create(admin);
  }

}
