package com.neoris.challenge.api.v1.resource;

import com.neoris.challenge.api.v1.resource.dto.ClientDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/clients/v1/client")
public interface ClientResource {

    @GetMapping(value = "/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    ClientDTO getClient(@PathVariable("clientId") Integer clientId);

    @PutMapping(value = "/{clientId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateClient(@PathVariable("clientId") Integer clientId, @RequestBody ClientDTO client);

    @PostMapping(value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void createClient(@RequestBody ClientDTO client);

    @DeleteMapping(value = "/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteClient(@PathVariable("clientId") Integer clientId);
}
