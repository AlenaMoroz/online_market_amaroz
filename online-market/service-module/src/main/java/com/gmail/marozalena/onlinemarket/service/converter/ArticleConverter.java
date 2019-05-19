package com.gmail.marozalena.onlinemarket.service.converter;

import com.gmail.marozalena.onlinemarket.repository.model.Article;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;

public interface ArticleConverter {

    Article fromDTO(ArticleDTO articleDTO);
    ArticleDTO toDTO(Article article);
}
