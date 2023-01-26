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
    DTOMapper dtoMapper;

    @Override
    public ClientDTO getClient(Integer clientId) {
        return clientService.getClient(clientId)
                .map(client -> dtoMapper.map(client, ClientDTO.class))
                .orElse(null);
    }

    @Override
    public void updateClient(Integer clientId, ClientDTO client) {
        Client newClient = new Client();
        newClient.setName(client.getName());
        newClient.setAge(client.getAge());
        newClient.setGender(client.getGender());
        newClient.setAddress(client.getAddress());
        newClient.setIdentification(client.getIdentification());
        newClient.setPassword(client.getPassword());
        newClient.setStatus(client.getStatus());
        clientService.updateClient(clientId, newClient);
    }

    @Override
    public void createClient(ClientDTO client) {
        Client newClient = new Client();
        newClient.setName(client.getName());
        newClient.setAge(client.getAge());
        newClient.setGender(client.getGender());
        newClient.setAddress(client.getAddress());
        newClient.setIdentification(client.getIdentification());
        newClient.setPassword(client.getPassword());
        newClient.setStatus(client.getStatus());
        clientService.createClient(newClient);
    }

    @Override
    public void deleteClient(Integer clientId) {
        clientService.deleteClient(clientId);
    }
}
