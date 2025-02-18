package com.halo.eventer.global.config;

import com.halo.eventer.global.security.exception.CustomAccessDeniedHandler;
import com.halo.eventer.global.security.exception.CustomAuthenticationEntryPoint;
import com.halo.eventer.global.security.filter.JwtAuthenticationFilter;
import com.halo.eventer.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Value("${aes.secret.key}")
    private String secretKey;

    @Value("${aes.salt}")
    private String salt;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .httpBasic().disable()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

//                .authorizeRequests()
//                .antMatchers("/", "/swagger-ui/**", "/v3/**","/swagger-ui.html").permitAll()
//                .antMatchers(HttpMethod.POST, "/concert","/concertInfo/**","/duration/**", "/festival/**","/mapCategory/**","/map/**", "/menu/**","/notice/**","/widget").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/concert","/concertInfo/**","/duration/**", "/festival/**","/mapCategory/**","/map/**", "/menu/**","/notice/**","/widget").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/concert","/concertInfo/**","/duration/**", "/festival/**","/mapCategory/**","/map/**", "/menu/**","/notice/**","/widget").hasRole("ADMIN")
//                .anyRequest().permitAll();
                .authorizeRequests()
                .antMatchers("/", "/swagger-ui/**", "/v3/**","/swagger-ui.html").permitAll()

                .antMatchers(HttpMethod.POST, "/widget/*","/vote", "/api/upWidgets","/stamp/user",
                        "/stamp","/stamp/mission","/splash","/notice","/notice/banner","/missingPerson","/middleBanner","/map","/mapCategory/*","/menu",
                        "/manager","/lostItem","/inquiry/forAdmin/*",
                        "/festival","/festival/*/color","/festival/*/logo","/festival/*/main-menu","/festival/*/entry","/festival/*/view","/festival/*/location",
                        "/duration/*","/api/downWidget","/concertInfo/*/name","/concert").hasRole("ADMIN")

                .antMatchers(HttpMethod.PATCH, "/widget","/vote/backOffice/*","/api/upWidgets","/stamp","/stamp/mission","/notice/banner","/notice/*",
                        "/missingPerson","/middleBanner","/middleBanner/rank","/map/*","/mapCategory/*","/mapCategory/categoryRank","/menu","/lostItem/*",
                        "/inquiry/forAdmin","/festival/*","/concertInfo/*","/concert/*").hasRole("ADMIN")

                .antMatchers(HttpMethod.DELETE, "/widget","/vote/backOffice/*","/api/upWidgets","/stamp/user/*","/stamp","/splash","/notice/*",
                        "/missingPerson","/middleBanner","/map/*","/mapCategory/*","/menu/*","/manager","/lostItem/*","/inquiry/forAdmin","/festival/*",
                        "/concertInfo/*","/concert/*").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/vote/backOffice", "/vote/backOffice/*","/stamp/users","/inquiry/forAdmin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/excel/stamp/download").hasAnyRole("ADMIN","USER")
                .anyRequest().permitAll();


        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "https://localhost:3000",
                "https://khucore.com",
                "http://khucore.localhost:3000",
                "http://business.localhost:3000",
                "http://business.localhost:3001",
                "https://wabaa.kr",
                "https://business.wabaa.kr",
                "https://adelante.wabauniv.com",
                "http://m.localhost:3000",
                "https://firefestivaljeju.com",
                "https://m.firefestivaljeju.com")
        );
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AesBytesEncryptor aesBytesEncryptor() {
        return new AesBytesEncryptor(secretKey, salt);
    }
}
