package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;

import java.util.List;

public interface ArticleService {
    PageDTO<ArticleDTO> getArticles(Integer page);

    ArticleDTO findByID(Long id);

    List<ArticleDTO> getAllArticles();

    void addArticle(ArticleDTO articleDTO);

    void deleteArticle(Long id);
}
