package com.neoris.challenge.service;

import com.neoris.challenge.api.v1.service.ClientService;
import com.neoris.challenge.api.v1.service.model.Client;
import com.neoris.challenge.repository.ClientRepository;
import com.neoris.challenge.repository.mapper.RepositoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Override
    public Optional<Client> getClient(Integer id) {
        log.info("Retrieving client info by ID: clientId={}", id);
        Optional<Client> clientFound = clientRepository.findById(id)
                .map(client -> repositoryMapper.map(client, Client.class));

        if (clientFound.isEmpty() || (clientFound.isPresent() && !clientFound.get().getStatus())) {
            log.error("Client not found: clientId={}", id);
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", id));
        }

        return clientFound;
    }

    @Override
    public void createClient(Client client) {
        log.info("Creating client: client={}", client);
        com.neoris.challenge.repository.entity.Client newClient =
                repositoryMapper.map(client, com.neoris.challenge.repository.entity.Client.class);
        com.neoris.challenge.repository.entity.Client clientSaved = clientRepository.save(newClient);
        log.info("Client saved: clientId={}", clientSaved.getId());
    }

    @Override
    public void updateClient(Integer id, Client client) {
        log.info("Updating client: clientId={}", id);
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
            log.info("Client saved: clientId={}", id);
        } else {
            log.error("Client not found: clientId={}", id);
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", id));
        }
    }

    @Override
    public void deleteClient(Integer id) {
        log.info("Deleting client: clientId={}", id);
        Optional<com.neoris.challenge.repository.entity.Client> clientToRemove = clientRepository.findById(id);
        if (clientToRemove.isEmpty()) {
            log.error("Client not found: clientId={}", id);
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", id));
        }
        clientToRemove.get().setStatus(false);
        clientRepository.save(clientToRemove.get());
        log.info("Client deleted: clientId={}", id);
    }
}
