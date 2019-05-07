package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    UserDTO loadUserByUsername(String name);

    void addUser(UserDTO userDTO);
}
