package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ArticleService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public RestApiController(UserService userService,
                             ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody @Valid UserDTO user) {
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/articles")
    public List<ArticleDTO> getArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/articles/{id}")
    public ArticleDTO getArticle(
            @PathVariable Long id
    ) {
        return articleService.findByID(id);
    }

    @PostMapping("/articles")
    public ResponseEntity addArticle(
           @RequestBody ArticleDTO articleDTO
    ) {
        articleService.addArticle(articleDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/articles/{id}")
    public ResponseEntity deleteArticle(
            @PathVariable Long id
    ) {
        articleService.deleteArticle(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
