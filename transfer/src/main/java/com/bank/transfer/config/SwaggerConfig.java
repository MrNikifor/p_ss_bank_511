package com.bank.transfer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "transfer",
        version = "1.0",
        description = "API documentation for Transfer microservice"
    )
)
public class SwaggerConfig {
}
