package com.bank.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ProfileApplication {
    public static void main(String[] args) {

        SpringApplication.run(ProfileApplication.class, args);

    }
}
