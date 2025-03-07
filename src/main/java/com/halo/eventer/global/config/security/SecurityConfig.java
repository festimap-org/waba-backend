package com.halo.eventer.global.config.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final CorsConfigurationSource corsConfig;
  private final AuthorizationConfig authorizationConfig;
  private final SecurityFilterConfig securityFilterConfig;

  public SecurityConfig(@Qualifier("customCorsConfigurationSource")CorsConfigurationSource corsConfig,
                        AuthorizationConfig authorizationConfig, SecurityFilterConfig securityFilterConfig) {
    this.corsConfig = corsConfig;
    this.authorizationConfig = authorizationConfig;
    this.securityFilterConfig = securityFilterConfig;
  }

  @Value("${aes.secret.key}")
  private String secretKey;

  @Value("${aes.salt}")
  private String salt;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .cors().configurationSource(corsConfig)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    authorizationConfig.configure(http);
    securityFilterConfig.configure(http);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AesBytesEncryptor aesBytesEncryptor() {
    return new AesBytesEncryptor(secretKey, salt);
  }
}
