package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ArticleService;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageDTO<ArticleDTO> articles = articleService.getArticles(page);
        model.addAttribute("articles", articles);
        int countOfPages = articles.getCountOfPages();
        if (page > countOfPages && countOfPages > 0) {
            page = countOfPages;
        }
        model.addAttribute("current", page);
        List<Integer> pages = IntStream.rangeClosed(1, countOfPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", pages);
        return "articles";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(Model model,
                              @RequestParam(value = "id") Long id) {
        ArticleDTO article = articleService.findByID(id);
        model.addAttribute("article", article);
        return "article";
    }
}
