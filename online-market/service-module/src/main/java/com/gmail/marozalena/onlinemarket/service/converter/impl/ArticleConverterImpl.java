package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Article;
import com.gmail.marozalena.onlinemarket.service.converter.ArticleConverter;
import com.gmail.marozalena.onlinemarket.service.converter.CommentConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.gmail.marozalena.onlinemarket.service.constant.DateConstants.PATTERN_FOR_DATE;
import static com.gmail.marozalena.onlinemarket.service.constant.SummaryLengthConstant.END_LENGTH_OF_SUMMARY;
import static com.gmail.marozalena.onlinemarket.service.constant.SummaryLengthConstant.FULL_LENGTH_OF_SUMMARY;
import static com.gmail.marozalena.onlinemarket.service.constant.SummaryLengthConstant.STRART_LENGTH_OF_SUMMARY;

@Component
public class ArticleConverterImpl implements ArticleConverter {

    public static final Logger logger = LoggerFactory.getLogger(ArticleConverterImpl.class);

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
            if (articleDTO.getDate() != null) {
                article.setDate(new SimpleDateFormat(PATTERN_FOR_DATE).parse(articleDTO.getDate()));
            } else {
                article.setDate(new Date());
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            article.setDate(new Date());
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
        try {
            articleDTO.setDate(new SimpleDateFormat(PATTERN_FOR_DATE).format(article.getDate()));
        } catch (NullPointerException e){
            logger.error(e.getMessage(), e);
            articleDTO.setDate(new SimpleDateFormat(PATTERN_FOR_DATE).format(new Date()));
        }
        if (article.getBody() != null) {
            if (article.getBody().length() > FULL_LENGTH_OF_SUMMARY) {
                articleDTO.setSummary(article.getBody().substring(
                        STRART_LENGTH_OF_SUMMARY, END_LENGTH_OF_SUMMARY) + "...");
            } else {
                articleDTO.setSummary(article.getBody());
            }
        }
        articleDTO.setComments(commentConverter.toDTO(article.getComments()));
        return articleDTO;
    }
}
