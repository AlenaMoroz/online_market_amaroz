package com.gmail.marozalena.onlinemarket.controller;

import com.gmail.marozalena.onlinemarket.service.ProfileService;
import com.gmail.marozalena.onlinemarket.service.RandomPasswordService;
import com.gmail.marozalena.onlinemarket.service.RoleService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private RandomPasswordService randomPasswordService;

    private AdminController adminController;

    private MockMvc mvc;

    @Before
    public void init() {
        adminController = new AdminController(userService, roleService,randomPasswordService);
        mvc = MockMvcBuilders.standaloneSetup(adminController).build();
        when(userService.getUsers(1)).thenReturn(generatePage());
    }

    @Test
    public void allUsersAreAddedToModelForUsersView() {
        Model model = new ExtendedModelMap();
        String users = adminController.getUsers(model, 1);
        assertThat(users, equalTo("users"));
        assertThat(model.asMap(), hasEntry("users", generatePage()));
    }

    private PageDTO<UserDTO> generatePage(){
        PageDTO<UserDTO> users = new PageDTO<>();
        List<UserDTO> list = new ArrayList<>();
        list.add(new UserDTO());
        list.add(new UserDTO());
        users.setList(list);
        users.setCountOfPages(1);
        return users;
    }

}
