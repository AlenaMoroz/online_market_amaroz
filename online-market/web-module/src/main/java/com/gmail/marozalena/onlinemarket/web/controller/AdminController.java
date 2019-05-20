package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ProfileService;
import com.gmail.marozalena.onlinemarket.service.RandomPasswordService;
import com.gmail.marozalena.onlinemarket.service.RoleService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/private")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private static final String redirectToUsersPage = "redirect:/private/users";

    private final UserService userService;
    private final RoleService roleService;
    private final RandomPasswordService randomPasswordService;

    @Autowired
    public AdminController(
            UserService userService,
            RoleService roleService,
            RandomPasswordService randomPasswordService) {
        this.userService = userService;
        this.roleService = roleService;
        this.randomPasswordService = randomPasswordService;
    }

    @GetMapping("/users")
    public String getUsers(Model model,
                           @RequestParam(value = "page", defaultValue = "1") Integer page) {
        model.addAttribute("roles", roleService.getRoles());
        PageDTO<UserDTO> users = userService.getUsers(page);
        model.addAttribute("users", users.getList());
        int countOfPages = users.getCountOfPages();
        if (page > countOfPages && countOfPages > 0) {
            page = countOfPages;
        }
        List<Integer> pages = IntStream.rangeClosed(1, countOfPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", pages);
        model.addAttribute("current", page);
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "users";
    }

    @PostMapping(value = "/users/delete")
    public String deleteUsers(@RequestParam("ids") List<Long> idUsers) {
        if (idUsers != null) {
            userService.deleteUsers(idUsers);
        }
        return redirectToUsersPage;
    }

    @PostMapping(value = "/users/{id}", params = "action=password")
    public String getPassword(@PathVariable Long id,
                              UserDTO userDTO) {
        String password = randomPasswordService.getRandomPassword();
        logger.info("For user with id: " + userDTO.getId() + " was generated new password: " + password);
        return redirectToUsersPage;
    }

    @PostMapping(value = "/users/{id}", params = "action=save")
    public String saveUser(@PathVariable Long id,
            @ModelAttribute(value = "userDTO") UserDTO userDTO) {
        userService.saveUser(userDTO);
        return redirectToUsersPage;
    }

    @GetMapping("/users/new")
    public String addUser(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.getRoles());
        return "add";
    }

    @PostMapping("/users/new")
    public String addUser(@ModelAttribute(value = "user") UserDTO userDTO) {
        userService.addUser(userDTO);
        return redirectToUsersPage;
    }

}
