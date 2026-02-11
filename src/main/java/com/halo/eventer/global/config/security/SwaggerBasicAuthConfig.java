package com.halo.eventer.global.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SwaggerBasicAuthConfig {

    @Bean
    public InMemoryUserDetailsManager swaggerUserDetailsManager(
            @Value("${SWAGGER_USERNAME}") String username,
            @Value("${SWAGGER_PASSWORD}") String password,
            PasswordEncoder passwordEncoder) {

        UserDetails swaggerUser = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles("SWAGGER")
                .build();

        return new InMemoryUserDetailsManager(swaggerUser);
    }
}
