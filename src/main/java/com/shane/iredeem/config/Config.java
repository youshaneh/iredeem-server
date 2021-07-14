package com.shane.iredeem.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Getter
    @Value("${jwt.public_key}")
    private String jwtPublicKey;
}
