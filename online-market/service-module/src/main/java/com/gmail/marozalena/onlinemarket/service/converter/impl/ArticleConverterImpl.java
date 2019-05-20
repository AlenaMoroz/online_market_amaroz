package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Article;
import com.gmail.marozalena.onlinemarket.service.converter.ArticleConverter;
import com.gmail.marozalena.onlinemarket.service.converter.CommentConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverterImpl implements ArticleConverter {

    private final UserConverter userConverter;
    private final CommentConverter commentConverter;

    @Autowired
    public ArticleConverterImpl(UserConverter userConverter,
                                CommentConverter commentConverter) {
        this.userConverter = userConverter;
        this.commentConverter = commentConverter;
    }

    @Override
    public Article fromDTO(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setPicture(articleDTO.getPicture());
        article.setSummary(articleDTO.getSummary());
        article.setTopic(articleDTO.getTopic());
        article.setUser(userConverter.fromUserDTO(articleDTO.getUser()));
        article.setDate(articleDTO.getDate());
        article.setBody(articleDTO.getBody());
        article.setComments(commentConverter.fromDTO(articleDTO.getComments()));
        return article;
    }

    @Override
    public ArticleDTO toDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setBody(article.getBody());
        articleDTO.setSummary(article.getSummary());
        articleDTO.setUser(userConverter.toUserDTO(article.getUser()));
        articleDTO.setTopic(article.getTopic());
        articleDTO.setPicture(article.getPicture());
        articleDTO.setDate(article.getDate());
        articleDTO.setComments(commentConverter.toDTO(article.getComments()));
        return articleDTO;
    }
}
