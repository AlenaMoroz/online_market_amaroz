package com.gmail.marozalena.onlinemarket.web.config;

import com.gmail.marozalena.onlinemarket.web.config.handler.AppUrlAuthenticationSuccessHandler;
import com.gmail.marozalena.onlinemarket.web.config.handler.LoginAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.ADMINISTRATOR;
import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.CUSTOMER_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.SALE_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.*;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppUrlAuthenticationSuccessHandler();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new LoginAccessDeniedHandler();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        URL_TO_USERS_PAGE,
                        URL_TO_USER_PAGE,
                        URL_TO_ADD_NEW_USER_PAGE,
                        URL_TO_DELETE_USERS_PAGE,
                        URL_TO_REVIEWS_PAGE,
                        URL_TO_DELETE_REVIEW_PAGE,
                        URL_TO_UPDATE_REVIEWS_PAGE)
                .hasAuthority(ADMINISTRATOR)
                .antMatchers(
                        URL_TO_PROFILE_PAGE,
                        URL_TO_PROFILE_PAGES,
                        URL_TO_ORDERS_PAGE_FOR_CUSTOMER_USER,
                        URL_TO_CREATE_ORDER,
                        URL_TO_CREATE_REVIEW_PAGE)
                .hasAuthority(CUSTOMER_USER)
                .antMatchers(
                        URL_TO_ARTICLES_PAGE,
                        URL_TO_ARTICLE_PAGE,
                        URL_TO_ITEMS_PAGE,
                        URL_TO_ITEM_PAGE)
                .hasAnyAuthority(SALE_USER, CUSTOMER_USER)
                .antMatchers(
                        URL_TO_ADD_NEW_ARTICLE_PAGE,
                        URL_TO_DELETE_ARTICLE_PAGE,
                        URL_TO_UPDATE_ARTICLE_PAGE,
                        URL_TO_DELETE_COMMENT_PAGE,
                        URL_TO_ORDERS_PAGE_FOR_SALE_USER,
                        URL_TO_ORDER_PAGE,
                        URL_TO_UPDATE_ORDER_PAGE)
                .hasAuthority(SALE_USER)
                .antMatchers(URL_TO_LOGIN_PAGE, "/error")
                .permitAll()
                .and()
                .formLogin()
                .loginPage(URL_TO_LOGIN_PAGE)
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }
}
