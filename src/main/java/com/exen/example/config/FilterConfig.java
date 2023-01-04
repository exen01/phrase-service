package com.exen.example.config;

import com.exen.example.filter.AuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
        final var filterBean = new FilterRegistrationBean<>(new AuthorizationFilter());
        filterBean.addUrlPatterns(
                "/phrase-service-public/search/*",
                "/phrase-service-public/communication/*",
                "/phrase-service-public/user/my-phrases",
                "/phrase-service-public/user/publish-phrase"
        );
        return filterBean;
    }
}
