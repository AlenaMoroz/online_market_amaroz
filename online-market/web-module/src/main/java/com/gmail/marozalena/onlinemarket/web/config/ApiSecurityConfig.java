package com.gmail.marozalena.onlinemarket.web.config;

import com.gmail.marozalena.onlinemarket.web.config.handler.ApiAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.SECURE_REST_API;

@Configuration
@Order(1)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public ApiSecurityConfig(PasswordEncoder passwordEncoder,
                                 UserDetailsService userDetailsService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest()
                .hasAuthority(SECURE_REST_API)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(apiAccessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AccessDeniedHandler apiAccessDeniedHandler() {
        return new ApiAccessDeniedHandler();
    }
}
