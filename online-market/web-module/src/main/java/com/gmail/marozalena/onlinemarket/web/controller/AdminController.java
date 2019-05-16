package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.RandomPasswordService;
import com.gmail.marozalena.onlinemarket.service.RoleService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import com.gmail.marozalena.onlinemarket.service.pagination.PaginationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final PaginationService paginationService;

    @Autowired
    public AdminController(
            UserService userService,
            RoleService roleService,
            RandomPasswordService randomPasswordService,
            PaginationService paginationService)
    {
        this.userService = userService;
        this.roleService = roleService;
        this.randomPasswordService = randomPasswordService;
        this.paginationService = paginationService;
    }

    @GetMapping("/users")
    public String getUsers(Model model,
                           @RequestParam(value = "page", defaultValue = "1") Integer page) {
        model.addAttribute("roles", roleService.getRoles());
        int countPages = paginationService.getCountPagesForPageWithUsers();
        if (page > countPages && countPages > 0) {
            page = countPages;
        }
        List<Integer> countOfPages = IntStream.rangeClosed(1, countPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", countOfPages);
        model.addAttribute("current", page);
        model.addAttribute("users", userService.getUsers(page));
        return "users";
    }

    @PostMapping(value = "/users/delete")
    public String deleteUsers(@RequestParam("ids") List<Long> idUsers) {
        if (idUsers != null) {
            userService.deleteUsers(idUsers);
        }
        return redirectToUsersPage;
    }

    @PostMapping(value = "/users", params = "action=password")
    public String getPassword(UserDTO userDTO) {
        String password = randomPasswordService.getRandomPassword();
        logger.info("For user with email: " + userDTO.getEmail() + " was generated new password: " + password);
        return redirectToUsersPage;
    }

    @PostMapping(value = "/users", params = "action=save")
    public String saveUser(UserDTO userDTO) {
        userService.saveUser(userDTO);
        return redirectToUsersPage;
    }

    @GetMapping("/users/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.getRoles());
        return "add";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute(value = "user") UserDTO userDTO) {
        userService.addUser(userDTO);
        return redirectToUsersPage;
    }

}
