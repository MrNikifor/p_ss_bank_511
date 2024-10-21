package com.bank.account.config;

import com.bank.account.mapper.AccountDetailsMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class MyConfig {
    @Bean
    public AccountDetailsMapper accountDetailsMapper() {
        return AccountDetailsMapper.INSTANCE;
    }
}
