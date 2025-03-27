package com.halo.eventer.global.config;

import com.halo.eventer.global.resolver.MemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MemberArgumentResolver memberArgumentResolver;

    public WebMvcConfig(MemberArgumentResolver memberIdArgumentResolver) {
        this.memberArgumentResolver = memberIdArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberArgumentResolver);
    }
}