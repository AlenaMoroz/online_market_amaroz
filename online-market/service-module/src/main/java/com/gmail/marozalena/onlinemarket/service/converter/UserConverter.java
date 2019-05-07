package com.gmail.marozalena.onlinemarket.service.converter;

import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;

public interface UserConverter {
    
    User fromUserDTO(UserDTO userDTO);

    UserDTO toUserDTO(User user);
}
