package com.neoris.challenge.api.v1.resource.mapper;

import com.neoris.challenge.api.v1.resource.dto.ClientDTO;
import com.neoris.challenge.api.v1.resource.dto.TransactionDTO;
import com.neoris.challenge.api.v1.service.model.Client;
import com.neoris.challenge.api.v1.service.model.Transaction;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class DTOMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Transaction.class, TransactionDTO.class)
                .byDefault()
                .register();

        factory.classMap(Client.class, ClientDTO.class)
                .byDefault()
                .register();
    }
}
