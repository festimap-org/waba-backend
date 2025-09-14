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

                .antMatchers(HttpMethod.POST, "/widget/*","/vote", "/api/upWidgets",
                        "/stamp","/stamp/mission","/splash","/middleBanner","/map","/mapCategory/*","/menu",
                        "/festival","/festival/*/color","/festival/*/logo","/festival/*/main-menu","/festival/*/entry","/festival/*/view","/festival/*/location",
                        "/duration/*","/api/downWidget","/concertInfo/*/name","/concert").hasRole("ADMIN")

                .antMatchers(HttpMethod.PATCH, "/widget","/vote/backOffice/*","/api/upWidgets","/stamp","/stamp/mission","/middleBanner","/middleBanner/rank","/map/*","/mapCategory/*","/mapCategory/categoryRank","/menu","/festival/*","/concertInfo/*","/concert/*").hasRole("ADMIN")

                .antMatchers(HttpMethod.DELETE, "/widget","/vote/backOffice/*","/api/upWidgets","/stamp/user/*","/stamp","/splash","/middleBanner","/map/*","/mapCategory/*","/menu/*","/festival/*",
                        "/concertInfo/*","/concert/*").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/vote/backOffice", "/vote/backOffice/*","/stamp/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/excel/stamp/download","/inquiry/forAdmin","/forAdmin/*").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST, "/notice","/notice/banner","/lostItem","/parking","/parking/announcement","/parking/place","/inquiry/forAdmin/*","/manager").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.PATCH, "/notice/banner","/notice/*","/lostItem/*","/missingPerson","/missingPerson/popup",
                        "/parking","/parking/place/congestion","/parking/place/location","/parking/place/state","/inquiry/forAdmin").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.DELETE, "/notice/*","/lostItem/*","/missingPerson","/parking/place","/inquiry/forAdmin","/manager").hasAnyRole("ADMIN","USER")

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
                "https://m.firefestivaljeju.com",
                "https://cherryblossom.festimap.kr",
                "https://www.djdrone.kr",
                "https://djdrone.kr",
                "https://m.mokpowshow.co.kr",
                "https://mokpowshow.co.kr/",
                "https://www.mokpowshow.co.kr",
                "https://wheat.festimap.kr",
                "https://adelante.festimap.kr",
                "https://festival.business.festimap.kr",
                "https://business.festimap.kr",
                "https://adelante.festimap.kr",
                "https://jejulhfestival.festimap.kr",
                "https://m.jejulhfestival.festimap.kr",
                "https://jejulhfestival.kr",
                "https://m.jejulhfestival.kr",
                "https://www.gdsunsa.com",
                "https://m.gdsunsa.com",
                "https://ysff.festimap.kr"
                )
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
