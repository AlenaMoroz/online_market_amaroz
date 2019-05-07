package com.gmail.marozalena.onlinemarket.converter;

import com.gmail.marozalena.onlinemarket.model.User;
import com.gmail.marozalena.onlinemarket.model.UserDTO;

public interface UserConverter {
    
    User fromUserDTO(UserDTO userDTO);

    UserDTO toUserDTO(User user);
}
