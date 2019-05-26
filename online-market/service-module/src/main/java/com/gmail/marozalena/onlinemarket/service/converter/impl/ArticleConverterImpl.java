package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Article;
import com.gmail.marozalena.onlinemarket.service.converter.ArticleConverter;
import com.gmail.marozalena.onlinemarket.service.converter.CommentConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        article.setTopic(articleDTO.getTopic());
        article.setUser(userConverter.fromUserDTO(articleDTO.getUser()));
        try {
            article.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(articleDTO.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        article.setBody(articleDTO.getBody());
        article.setComments(commentConverter.fromDTO(articleDTO.getComments()));
        return article;
    }

    @Override
    public ArticleDTO toDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setBody(article.getBody());
        articleDTO.setUser(userConverter.toUserDTO(article.getUser()));
        articleDTO.setTopic(article.getTopic());
        articleDTO.setPicture(article.getPicture());
        articleDTO.setDate(article.getDate().toString());
        if (article.getBody().length() > 196) {
            articleDTO.setSummary(article.getBody().substring(0, 196) + "...");
        }else {
            articleDTO.setSummary(article.getBody());
        }
        articleDTO.setComments(commentConverter.toDTO(article.getComments()));
        return articleDTO;
    }
}
