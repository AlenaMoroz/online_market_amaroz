package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;

import java.util.List;

public interface UserService {
    PageDTO<UserDTO> getUsers(Integer page);

    UserDTO loadUserByEmail(String email);

    void addUser(UserDTO userDTO);

    void deleteUsers(List<Long> idUsers);

    void saveUser(UserDTO userDTO);
}
