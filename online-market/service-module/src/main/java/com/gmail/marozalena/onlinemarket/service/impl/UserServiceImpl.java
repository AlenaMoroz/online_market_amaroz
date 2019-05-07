package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDTO> getUsers() {
        return null;
    }

    @Override
    public UserDTO loadUserByUsername(String name) {
        return null;
    }

    @Override
    public void addUser(UserDTO userDTO) {

    }
}
