package com.halo.eventer.global.config.security;

import com.halo.eventer.global.security.filter.JwtAuthenticationFilter;
import com.halo.eventer.global.security.filter.SecurityExceptionFilter;
import com.halo.eventer.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
  private final JwtProvider jwtProvider;

  public void configure(HttpSecurity http) throws Exception {
    http.addFilterAfter(new SecurityExceptionFilter(), SecurityContextPersistenceFilter.class)
        .addFilterBefore(
            new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
  }
}
