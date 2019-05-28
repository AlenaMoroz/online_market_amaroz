package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ArticleService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.CUSTOMER_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ADD_NEW_ARTICLE_PAGE;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ARTICLES_PAGE;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ARTICLE_PAGE;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_DELETE_ARTICLE_PAGE;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_UPDATE_ARTICLE_PAGE;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService,
                             UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping(URL_TO_ARTICLES_PAGE)
    public String getArticles(Model model,
                              @RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageDTO<ArticleDTO> articles = articleService.getArticles(page);
        model.addAttribute("articles", articles.getList());
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

    @GetMapping(URL_TO_ARTICLE_PAGE)
    public String getArticle(Model model,
                             @PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserDTO userDTO = userService.loadUserByEmail(email);
        if (userDTO.getRole().getName().equals(CUSTOMER_USER)) {
            ArticleDTO article = articleService.findByID(id);
            model.addAttribute("article", article);
            return "article";
        } else {
            ArticleDTO article = articleService.findByID(id);
            model.addAttribute("article", article);
            return "articleForSaleUser";
        }
    }

    @PostMapping(URL_TO_DELETE_ARTICLE_PAGE)
    public String deleteAtricle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return "articles";
    }

    @GetMapping(URL_TO_ADD_NEW_ARTICLE_PAGE)
    public String cteateArticleForm(Model model) {
        model.addAttribute("article", new ArticleDTO());
        return "newArticle";
    }

    @PostMapping(URL_TO_ADD_NEW_ARTICLE_PAGE)
    public String createArticle(@ModelAttribute(value = "article") ArticleDTO articleDTO,
                                @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/articles/new";
        }
        articleService.createArticle(articleDTO, file);
        return "redirect:/articles";
    }

    @PostMapping(URL_TO_UPDATE_ARTICLE_PAGE)
    public String updateArticle(@PathVariable Long id,
                                ArticleDTO articleDTO) {
        articleService.updateArticle(articleDTO);
        return "redirect:/articles/" + id;
    }

    @GetMapping("/images/{id}")
    public void getPicture(@PathVariable Long id,
                           HttpServletResponse response) throws IOException {
        String picture = articleService.getNameOfPicture(id);
        FileInputStream input = new FileInputStream(System.getProperty("java.io.tmpdir") + picture);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(input, response.getOutputStream());
    }
}
