package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers(Integer page);

    UserDTO loadUserByUsername(String name);

    void addUser(UserDTO userDTO);

    void deleteUsers(List<Long> idUsers);

    void saveUser(UserDTO userDTO);

    int getCountPages();
}
