package com.lemonchat;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lemonchat.mappers.AccountMapper;

@Configuration
public class MapperConfig {

    @Bean
    public AccountMapper accountMapper() {
        return Mappers.getMapper(AccountMapper.class);
    }
}
