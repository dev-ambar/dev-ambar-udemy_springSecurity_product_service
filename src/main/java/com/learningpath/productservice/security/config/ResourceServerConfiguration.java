package com.learningpath.productservice.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String PRODUCT_RESOURCE_ID = "product-service";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/productapi/products/{id:^[A-Za-z0-9]*$}","/productapi/products").hasAnyRole("ADMIN","USER")
                .mvcMatchers(HttpMethod.POST,"/productapi/products").hasRole("ADMIN")
                .anyRequest().denyAll().and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(PRODUCT_RESOURCE_ID);
    }
}
