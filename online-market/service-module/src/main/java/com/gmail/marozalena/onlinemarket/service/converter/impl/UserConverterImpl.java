package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.service.converter.RoleConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserConverterImpl implements UserConverter {

    private final RoleConverter roleConverter;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public User fromUserDTO(UserDTO userDTO) {
        if(userDTO == null){
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setPatronymic(userDTO.getPatronymic());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(roleConverter.fromRoleDTO(userDTO.getRole()));
        return user;
    }

    @Override
    public UserDTO toUserDTO(User user) {
        if(user == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setSurname(user.getSurname());
        userDTO.setName(user.getName());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(roleConverter.toRoleDTO(user.getRole()));
        return userDTO;
    }
}
