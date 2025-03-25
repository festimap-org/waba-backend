package com.halo.eventer.global.config;

import com.halo.eventer.global.config.security.AuthorizationConfig;
import com.halo.eventer.global.config.security.CorsConfig;
import com.halo.eventer.global.config.security.JwtAuthenticationFilterConfig;
import com.halo.eventer.global.security.exception.CustomAccessDeniedHandler;
import com.halo.eventer.global.security.exception.CustomAuthenticationEntryPoint;
import com.halo.eventer.global.security.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfigurationSource;


@TestConfiguration
public class ControllerTestSecurityBeans {

    // 테스트 전용 CorsConfigurationSource 빈 생성
    @Bean
    @Qualifier("customCorsConfigurationSource")
    public CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfig().customCorsConfigurationSource();
    }

    @Bean
    public AuthorizationConfig authorizationConfig() {
        return new AuthorizationConfig();
    }

    @Bean
    public JwtAuthenticationFilterConfig securityFilterConfig(JwtProvider jwtProvider) {
        return new JwtAuthenticationFilterConfig(jwtProvider);
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }
}
