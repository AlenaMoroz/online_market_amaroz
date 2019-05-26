package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    PageDTO<ArticleDTO> getArticles(Integer page);

    ArticleDTO findByID(Long id);

    List<ArticleDTO> getAllArticles();

    void addArticle(ArticleDTO articleDTO);

    void deleteArticle(Long id);

    void createArticle(ArticleDTO articleDTO, MultipartFile file);

    void updateArticle(ArticleDTO articleDTO);
}
