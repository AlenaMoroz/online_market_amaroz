package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDTO userDTO = userService.loadUserByUsername(name);
        if (userDTO == null) {
            throw new UsernameNotFoundException("User with name = " + name + "not found");
        }
        return new AppUserPrincipal(userDTO);
    }
}
