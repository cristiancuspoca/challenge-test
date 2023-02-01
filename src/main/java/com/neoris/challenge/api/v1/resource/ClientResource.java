package com.neoris.challenge.api.v1.resource;

import com.neoris.challenge.api.v1.resource.dto.ClientDTO;
import com.neoris.challenge.api.v1.resource.groupvalidation.Create;
import com.neoris.challenge.api.v1.resource.groupvalidation.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Validated
@RequestMapping(value = "/api/clients/v1/client")
public interface ClientResource {

    @GetMapping(value = "/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    ClientDTO getClient(@PathVariable("clientId") @NotNull Integer clientId);

    @PutMapping(value = "/{clientId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateClient(@PathVariable("clientId") @NotNull Integer clientId,
                      @RequestBody @Validated({Update.class, Default.class}) ClientDTO client);

    @PostMapping(value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void createClient(@RequestBody @Validated({Create.class, Default.class}) ClientDTO client);

    @DeleteMapping(value = "/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteClient(@PathVariable("clientId") @NotNull Integer clientId);
}
