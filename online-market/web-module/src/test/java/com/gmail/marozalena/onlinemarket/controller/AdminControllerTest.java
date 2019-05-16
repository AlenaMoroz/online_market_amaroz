package com.gmail.marozalena.onlinemarket.controller;

import com.gmail.marozalena.onlinemarket.service.RandomPasswordService;
import com.gmail.marozalena.onlinemarket.service.RoleServise;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import com.gmail.marozalena.onlinemarket.web.controller.AdminController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private RoleServise roleServise;
    @Mock
    private RandomPasswordService randomPasswordService;

    private AdminController adminController;

    private MockMvc mvc;

    private List<UserDTO> users = asList(generateUserDTO(), generateUserDTO());

    @Before
    public void init() {
        adminController = new AdminController(userService, roleServise,randomPasswordService);
        mvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.getUsers(1)).thenReturn(users);
    }

    @Test
    public void allUsersAreAddedToModelForUsersView() {
        Model model = new ExtendedModelMap();
        String users = adminController.getUsers(model, 1);
        assertThat(users, equalTo("users"));
        assertThat(model.asMap(), hasEntry("users", this.users));
    }

    private UserDTO generateUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Name");
        userDTO.setSurname("Name");
        userDTO.setPatronymic("Name");
        userDTO.setEmail("Email");
        userDTO.setPassword("Password");
        userDTO.setRole(new RoleDTO());
        return userDTO;
    }

}
