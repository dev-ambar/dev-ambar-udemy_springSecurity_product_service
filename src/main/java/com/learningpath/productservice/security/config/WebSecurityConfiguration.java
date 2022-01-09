package com.learningpath.productservice.security.config;

import com.learningpath.productservice.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.formLogin();*/
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/productapi/products/{id:^[A-Za-z0-9]*$}","/productapi/products","/index","/showProduct","/productDetails").hasAnyRole("ADMIN","USER")
                .mvcMatchers(HttpMethod.GET,"/showCreateProductPage","/createProductResponse","/createProduct").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/productapi/products","/saveProduct").hasRole("ADMIN")
                .mvcMatchers("/","/login","/showReg","/registerUser","/logout").permitAll()
                .anyRequest().denyAll().and().logout().logoutSuccessUrl("/");

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
