package com.ecommerce.dao;

import com.ecommerce.models.Client;

public class ClientDAO extends GenericDAO<Client> {

  public ClientDAO() {
    super(Client.class);
  }

  @Override
  public void create(Client client) {
    Client existingClient = findById(client.getId());

    if (existingClient.getUsername().equals(client.getUsername())) {
      throw new Error("Já exite um usuário com esse username.");
    }

    if (existingClient.getEmail().equals(client.getEmail())) {
      throw new Error("Já exite um usuário com esse email.");
    }

    super.create(client);
  }
}
