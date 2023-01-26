package com.neoris.challenge.repository.mapper;

import com.neoris.challenge.repository.entity.Account;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountRepositoryMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Account.class, com.neoris.challenge.api.v1.service.model.Account.class)
                .byDefault()
                .register();
    }
}
