package com.neoris.challenge.api.v1.service;

import com.neoris.challenge.api.v1.service.model.Client;

import java.util.Optional;

public interface ClientService {
    Optional<Client> getClient(Integer id);
    void createClient(Client client);
    void updateClient(Integer id, Client client);
    void deleteClient(Integer id);
}
