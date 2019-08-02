package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ArticleService;
import com.gmail.marozalena.onlinemarket.service.ItemService;
import com.gmail.marozalena.onlinemarket.service.OrderService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.ItemDTO;
import com.gmail.marozalena.onlinemarket.service.model.OrderDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserService userService;
    private final ArticleService articleService;
    private final ItemService itemService;
    private final OrderService orderService;

    @Autowired
    public RestApiController(UserService userService,
                             ArticleService articleService,
                             ItemService itemService,
                             OrderService orderService) {
        this.userService = userService;
        this.articleService = articleService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody @Valid UserDTO user) {
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/articles")
    public List<ArticleDTO> getArticles() {
        return articleService.getArticles();
    }

    @GetMapping("/articles/{id}")
    public ArticleDTO getArticle(
            @PathVariable Long id
    ) {
        return articleService.findByID(id);
    }

    @PostMapping("/articles")
    public ResponseEntity addArticle(
            @RequestBody @Valid ArticleDTO articleDTO
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserDTO userDTO = userService.loadUserByEmail(email);
        articleService.addArticle(articleDTO, userDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/articles/{id}")
    public ResponseEntity deleteArticle(
            @PathVariable Long id
    ) {
        articleService.deleteArticle(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/items")
    public List<ItemDTO> getItems() {
        return itemService.findItems();
    }

    @GetMapping("/items/{id}")
    public ItemDTO getItem(
            @PathVariable Long id
    ) {
        return itemService.findByID(id);
    }

    @PostMapping("/items")
    public ResponseEntity addItem(
            @RequestBody @Valid ItemDTO itemDTO
    ) {
        itemService.addItem(itemDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/items/{id}")
    public ResponseEntity deleteItem(
            @PathVariable Long id
    ) {
        itemService.deleteItem(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/orders")
    public List<OrderDTO> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/orders/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleCustomException(NullPointerException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

}
