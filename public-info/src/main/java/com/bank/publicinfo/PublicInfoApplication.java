package com.bank.publicinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
@EnableAspectJAutoProxy
public class PublicInfoApplication {
    public static void main(String[] args) {

        SpringApplication.run(PublicInfoApplication.class, args);

    }
}
