package com.halo.eventer.global.config.security;

import com.halo.eventer.global.security.exception.CustomAccessDeniedHandler;
import com.halo.eventer.global.security.exception.CustomAuthenticationEntryPoint;
import com.halo.eventer.global.security.filter.JwtAuthenticationFilter;
import com.halo.eventer.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final JwtProvider jwtProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                        new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }
}
