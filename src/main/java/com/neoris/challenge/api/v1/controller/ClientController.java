package com.neoris.challenge.api.v1.controller;

import com.neoris.challenge.api.v1.resource.dto.ClientDTO;
import com.neoris.challenge.api.v1.resource.ClientResource;
import com.neoris.challenge.api.v1.resource.mapper.DTOMapper;
import com.neoris.challenge.api.v1.service.ClientService;
import com.neoris.challenge.api.v1.service.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("ClientController")
public class ClientController implements ClientResource {

    @Autowired
    private ClientService clientService;

    @Autowired
    private DTOMapper dtoMapper;

    @Override
    public ClientDTO getClient(Integer clientId) {
        return clientService.getClient(clientId)
                .map(client -> dtoMapper.map(client, ClientDTO.class))
                .orElse(null);
    }

    @Override
    public void updateClient(Integer clientId, ClientDTO client) {
        Client updateClient = dtoMapper.map(client, Client.class);
        clientService.updateClient(clientId, updateClient);
    }

    @Override
    public void createClient(ClientDTO client) {
        Client newClient = dtoMapper.map(client, Client.class);
        clientService.createClient(newClient);
    }

    @Override
    public void deleteClient(Integer clientId) {
        clientService.deleteClient(clientId);
    }
}
