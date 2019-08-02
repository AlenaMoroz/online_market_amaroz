package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ProfileConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.RoleConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.UserConverterImpl;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserConverterTest {

    private UserConverter userConverter;

    @Before
    public void init() {
        userConverter = new UserConverterImpl(
                new RoleConverterImpl(),
                new ProfileConverterImpl());
    }

    @Test
    public void shouldConvertUserDTOWithIdToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(2L);
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getId(), user.getId());
    }

    @Test
    public void shouldConvertUserDTOWithEmailToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("Email");
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    public void shouldConvertUserDTOWithPasswordToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    public void shouldConvertUserDTOWithIdRoleUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(new RoleDTO(1L, "Admin"));
        User user = userConverter.fromUserDTO(userDTO);
        Assert.assertEquals(userDTO.getRole().getRole(), user.getRole().getRole());
    }

    @Test
    public void shouldConvertUserWithIdToUserDTO() {
        User user = new User();
        user.setId(2L);
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getId(), userDTO.getId());
    }

    @Test
    public void shouldConvertUserWithEmailToUserDTO() {
        User user = new User();
        user.setEmail("Email");
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void shouldConvertUserWithPasswordToUserDTO() {
        User user = new User();
        user.setPassword("password");
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void shouldConvertUserWithIdRoleUserDTO() {
        User user = new User();
        user.setRole(new Role(1L, "Admin"));
        UserDTO userDTO = userConverter.toUserDTO(user);
        Assert.assertEquals(user.getRole().getRole(), userDTO.getRole().getRole());
    }
}
