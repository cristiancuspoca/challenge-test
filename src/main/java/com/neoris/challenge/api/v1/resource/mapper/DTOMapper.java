package com.neoris.challenge.api.v1.resource.mapper;

import com.neoris.challenge.api.v1.resource.dto.AccountDTO;
import com.neoris.challenge.api.v1.resource.dto.ClientDTO;
import com.neoris.challenge.api.v1.resource.dto.TransactionDTO;
import com.neoris.challenge.api.v1.service.model.Account;
import com.neoris.challenge.api.v1.service.model.Client;
import com.neoris.challenge.api.v1.service.model.Transaction;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class DTOMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(TransactionDTO.class, Transaction.class)
                .byDefault()
                .register();

        factory.classMap(ClientDTO.class, Client.class)
                .byDefault()
                .register();

        factory.classMap(AccountDTO.class, Account.class)
                .byDefault()
                .register();
    }
}
