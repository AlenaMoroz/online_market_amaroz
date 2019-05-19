package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;

public interface ArticleService {
    PageDTO<ArticleDTO> getArticles(Integer page);

    ArticleDTO findByID(Long id);
}
