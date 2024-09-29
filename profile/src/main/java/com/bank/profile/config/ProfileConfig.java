package com.bank.profile.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.bank.profile")
@EnableAspectJAutoProxy
public class ProfileConfig {
}
