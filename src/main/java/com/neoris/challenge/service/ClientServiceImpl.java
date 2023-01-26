package com.neoris.challenge.service;

import com.neoris.challenge.api.v1.service.ClientService;
import com.neoris.challenge.api.v1.service.model.Client;
import com.neoris.challenge.repository.ClientRepository;
import com.neoris.challenge.repository.mapper.RepositoryMapper;
import com.neoris.challenge.type.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Override
    public Optional<Client> getClient(Integer id) {
        Optional<Client> clientFound = clientRepository.findById(id)
                .map(client -> repositoryMapper.map(client, Client.class));

        if (clientFound.isEmpty() || (clientFound.isPresent() && !clientFound.get().getStatus())) {
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", id));
        }

        return clientFound;
    }

    @Override
    public void createClient(Client client) {
        com.neoris.challenge.repository.entity.Client newClient = new com.neoris.challenge.repository.entity.Client();
        Gender gender = client.getGender();
        newClient.setName(client.getName());
        newClient.setAge(client.getAge());
        newClient.setGender(gender != null ? gender.name() : null);
        newClient.setAddress(client.getAddress());
        newClient.setIdentification(client.getIdentification());
        newClient.setPassword(client.getPassword());
        newClient.setPhone(client.getPhone());
        newClient.setStatus(client.getStatus());
        clientRepository.save(newClient);
    }

    @Override
    public void updateClient(Integer id, Client client) {
        Optional<com.neoris.challenge.repository.entity.Client> clientUpdate = clientRepository.findById(id);
        if (clientUpdate.isPresent() && clientUpdate.get().getStatus()) {
            Optional.ofNullable(client.getName()).ifPresent(clientUpdate.get()::setName);
            Optional.ofNullable(client.getAge()).ifPresent(clientUpdate.get()::setAge);
            Optional.ofNullable(client.getAddress()).ifPresent(clientUpdate.get()::setAddress);
            Optional.ofNullable(client.getIdentification()).ifPresent(clientUpdate.get()::setIdentification);
            Optional.ofNullable(client.getPassword()).ifPresent(clientUpdate.get()::setPassword);
            Optional.ofNullable(client.getPhone()).ifPresent(clientUpdate.get()::setPhone);
            Optional.ofNullable(client.getGender()).ifPresent(value -> clientUpdate.get().setGender(value.name()));

            clientRepository.save(clientUpdate.get());
        } else {
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", id));
        }
    }

    @Override
    public void deleteClient(Integer id) {
        Optional<com.neoris.challenge.repository.entity.Client> clientToRemove = clientRepository.findById(id);
        if (clientToRemove.isEmpty()) {
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", id));
        }
        clientToRemove.get().setStatus(false);
        clientRepository.save(clientToRemove.get());
    }
}
