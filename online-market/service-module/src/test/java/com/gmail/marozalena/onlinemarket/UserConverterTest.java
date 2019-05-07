package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.converter.impl.UserConverterImpl;
import com.gmail.marozalena.onlinemarket.model.Role;
import com.gmail.marozalena.onlinemarket.model.RoleDTO;
import com.gmail.marozalena.onlinemarket.model.User;
import com.gmail.marozalena.onlinemarket.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserConverterTest {

    private UserConverter userConverter;

    @Before
    public void init(){
        userConverter = new UserConverterImpl();
    }

    @Test
    public void shouldConvertUserDTOWithIdToUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(2L);
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getId(), user.getId());
    }

    @Test
    public void shouldConvertUserDTOWithSurnameToUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setSurname("Surname");
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getSurname(), user.getSurname());
    }

    @Test
    public void shouldConvertUserDTOWithNameToUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Name");
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getName(), user.getName());
    }

    @Test
    public void shouldConvertUserDTOWithPatronymicToUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setPatronymic("Patronymic");
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getPatronymic(), user.getPatronymic());
    }

    @Test
    public void shouldConvertUserDTOWithEmailToUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("Email");
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    public void shouldConvertUserDTOWithIdRoleUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(new RoleDTO(1L, "Administrator"));
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getRole().getName(), user.getRole().getName());
    }

    @Test
    public void shouldConvertUserWithIdToUserDTO(){
        User user = new User();
        user.setId(2L);
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getId(), userDTO.getId());
    }

    @Test
    public void shouldConvertUserWithSurnameToUserDTO(){
        User user = new User();
        user.setSurname("Surname");
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getSurname(), userDTO.getSurname());
    }

    @Test
    public void shouldConvertUserWithNameToUserDTO(){
        User user = new User();
        user.setName("Name");
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getName(), userDTO.getName());
    }

    @Test
    public void shouldConvertUserWithPatronymicToUserDTO(){
        User user = new User();
        user.setPatronymic("Patronymic");
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getPatronymic(), userDTO.getPatronymic());
    }

    @Test
    public void shouldConvertUserWithEmailToUserDTO(){
        User user = new User();
        user.setEmail("Email");
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void shouldConvertUserWithIdRoleUserDTO(){
        User user = new User();
        user.setRole(new Role(1L, "Administrator"));
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getRole().getName(), userDTO.getRole().getName());
    }
}
