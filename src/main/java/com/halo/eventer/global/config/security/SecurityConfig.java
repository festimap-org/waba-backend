package com.halo.eventer.global.config.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfigurationSource;

import com.halo.eventer.global.constants.SecurityConstants;

import com.halo.eventer.global.security.exception.CustomAccessDeniedHandler;
import com.halo.eventer.global.security.exception.CustomAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CorsConfigurationSource corsConfig;
    private final AuthorizationConfig authorizationConfig;
    private final JwtAuthenticationFilterConfig jwtAuthenticationFilterConfig;
    private final SecurityExceptionFilterConfig securityExceptionFilterConfig;
    private final FieldOpsFilterConfig fieldOpsFilterConfig;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(
            @Qualifier("customCorsConfigurationSource") CorsConfigurationSource corsConfig,
            AuthorizationConfig authorizationConfig,
            JwtAuthenticationFilterConfig jwtAuthenticationFilterConfig,
            SecurityExceptionFilterConfig securityExceptionFilterConfig,
            FieldOpsFilterConfig fieldOpsFilterConfig,
            PasswordEncoder passwordEncoder) {
        this.corsConfig = corsConfig;
        this.authorizationConfig = authorizationConfig;
        this.jwtAuthenticationFilterConfig = jwtAuthenticationFilterConfig;
        this.securityExceptionFilterConfig = securityExceptionFilterConfig;
        this.fieldOpsFilterConfig = fieldOpsFilterConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Order(0)
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http, InMemoryUserDetailsManager swaggerUserDetailsManager)
            throws Exception {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("swagger");
        entryPoint.afterPropertiesSet();

        http.securityMatcher(SecurityConstants.SWAGGER_URLS)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(swaggerAuthenticationProvider(swaggerUserDetailsManager))
                .authorizeHttpRequests(auth -> auth.anyRequest().hasRole("SWAGGER"))
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
                .formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfig))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        authorizationConfig.configure(http);
        jwtAuthenticationFilterConfig.configure(http);
        fieldOpsFilterConfig.configure(http);
        securityExceptionFilterConfig.configure(http);

        http.exceptionHandling(ex -> ex.accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }

    private DaoAuthenticationProvider swaggerAuthenticationProvider(InMemoryUserDetailsManager userDetailsManager) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsManager);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
