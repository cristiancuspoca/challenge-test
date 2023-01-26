package com.neoris.challenge.repository.mapper;

import com.neoris.challenge.repository.entity.Client;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientRepositoryMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Client.class, com.neoris.challenge.api.v1.service.model.Client.class)
                .byDefault()
                .register();
    }
}