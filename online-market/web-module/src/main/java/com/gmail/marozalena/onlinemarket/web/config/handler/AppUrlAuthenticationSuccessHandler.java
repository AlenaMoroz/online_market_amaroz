package com.gmail.marozalena.onlinemarket.web.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.ADMINISTRATOR;
import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.CUSTOMER_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.SALE_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.SECURE_REST_API;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ARTICLES_PAGE;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ARTICLE_PAGE;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_LOGIN_PAGE;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_USERS_PAGE;

public class AppUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppUrlAuthenticationSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Authentication authentication) throws IOException {

        String url = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to" + url);
        }
        redirectStrategy.sendRedirect(request, response, url);
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case ADMINISTRATOR:
                    return URL_TO_USERS_PAGE;
                case CUSTOMER_USER:
                    return URL_TO_ARTICLES_PAGE;
                case SALE_USER:
                    return URL_TO_ARTICLES_PAGE;
                case SECURE_REST_API:
                    return URL_TO_LOGIN_PAGE;
            }
        }
        throw new IllegalStateException();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
