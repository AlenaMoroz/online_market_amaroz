package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.service.converter.ProfileConverter;
import com.gmail.marozalena.onlinemarket.service.converter.RoleConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    private final RoleConverter roleConverter;
    private final ProfileConverter profileConverter;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter,
                             ProfileConverter profileConverter) {
        this.roleConverter = roleConverter;
        this.profileConverter = profileConverter;
    }

    @Override
    public User fromUserDTO(UserDTO userDTO) {
        if(userDTO == null){
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(roleConverter.fromRoleDTO(userDTO.getRole()));
        user.setProfile(profileConverter.fromDTO(userDTO.getProfile()));
        return user;
    }

    @Override
    public UserDTO toUserDTO(User user) {
        if(user == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(roleConverter.toRoleDTO(user.getRole()));
        userDTO.setProfile(profileConverter.toDTO(user.getProfile()));
        return userDTO;
    }
}
