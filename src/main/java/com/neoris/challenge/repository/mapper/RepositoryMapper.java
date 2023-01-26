package com.neoris.challenge.repository.mapper;

import com.neoris.challenge.repository.entity.Account;
import com.neoris.challenge.repository.entity.Client;
import com.neoris.challenge.repository.entity.Transaction;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class RepositoryMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Transaction.class, com.neoris.challenge.api.v1.service.model.Transaction.class)
                .byDefault()
                .register();

        factory.classMap(Account.class, com.neoris.challenge.api.v1.service.model.Account.class)
                .byDefault()
                .register();

        factory.classMap(Client.class, com.neoris.challenge.api.v1.service.model.Client.class)
                .byDefault()
                .register();
    }
}
