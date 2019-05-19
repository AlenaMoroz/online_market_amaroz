package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Article;
import com.gmail.marozalena.onlinemarket.service.converter.ArticleConverter;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverterImpl implements ArticleConverter {
    @Override
    public Article fromDTO(ArticleDTO articleDTO) {
        return null;
    }

    @Override
    public ArticleDTO toDTO(Article article) {
        return null;
    }
}
