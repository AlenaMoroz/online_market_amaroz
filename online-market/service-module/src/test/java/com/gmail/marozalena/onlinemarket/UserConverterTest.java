package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.RoleConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.UserConverterImpl;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserConverterTest {

    private UserConverter userConverter;

    private RoleDTO roleDTO = new RoleDTO(1L, "Admin");
    private Role role = new Role(1L, "Admin");

    @Before
    public void init() {
        userConverter = new UserConverterImpl(new RoleConverterImpl());
    }

    @Test
    public void shouldConvertUserDTOWithIdToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(2L);
        userDTO.setRole(roleDTO);
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getId(), user.getId());
    }

    @Test
    public void shouldConvertUserDTOWithSurnameToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setSurname("Surname");
        userDTO.setRole(roleDTO);
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getSurname(), user.getSurname());
    }

    @Test
    public void shouldConvertUserDTOWithNameToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Name");
        userDTO.setRole(roleDTO);
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getName(), user.getName());
    }

    @Test
    public void shouldConvertUserDTOWithPatronymicToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPatronymic("Patronymic");
        userDTO.setRole(roleDTO);
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getPatronymic(), user.getPatronymic());
    }

    @Test
    public void shouldConvertUserDTOWithEmailToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("Email");
        userDTO.setRole(roleDTO);
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    public void shouldConvertUserDTOWithIdRoleUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(roleDTO);
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getRole().getName(), user.getRole().getName());
    }

    @Test
    public void shouldConvertUserWithIdToUserDTO() {
        User user = new User();
        user.setId(2L);
        user.setRole(role);
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getId(), userDTO.getId());
    }

    @Test
    public void shouldConvertUserWithSurnameToUserDTO() {
        User user = new User();
        user.setSurname("Surname");
        user.setRole(role);
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getSurname(), userDTO.getSurname());
    }

    @Test
    public void shouldConvertUserWithNameToUserDTO() {
        User user = new User();
        user.setName("Name");
        user.setRole(role);
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getName(), userDTO.getName());
    }

    @Test
    public void shouldConvertUserWithPatronymicToUserDTO() {
        User user = new User();
        user.setPatronymic("Patronymic");
        user.setRole(role);
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getPatronymic(), userDTO.getPatronymic());
    }

    @Test
    public void shouldConvertUserWithEmailToUserDTO() {
        User user = new User();
        user.setEmail("Email");
        user.setRole(role);
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void shouldConvertUserWithIdRoleUserDTO() {
        User user = new User();
        user.setRole(role);
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getRole().getName(), userDTO.getRole().getName());
    }
}
