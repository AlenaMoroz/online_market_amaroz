package com.gmail.marozalena.onlinemarket.controller;

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

    private AdminController adminController;

    private MockMvc mvc;

    private List<UserDTO> users = asList(new UserDTO(
                    "Name1", "Name2", "Name3", "Email", "Password",
                    new RoleDTO(2L, "Sale User"), true),
            new UserDTO(
                    "Name1.1", "Name2.1", "Name3.1", "Email1", "Password",
                    new RoleDTO(3L, "Customer User"), false));

    @Before
    public void init() {
        adminController = new AdminController(userService, roleServise);
        mvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.getUsers(1)).thenReturn(users);
    }

    @Test
    public void allUsersAreAddedToModelForUsersView() {
        Model model = new ExtendedModelMap();
        String users = adminController.getUsersWithPage(model, 1);
        assertThat(users, equalTo("users"));
        assertThat(model.asMap(), hasEntry("users", this.users));
    }

}
