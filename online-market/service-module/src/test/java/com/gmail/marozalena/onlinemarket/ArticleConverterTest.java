package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.repository.model.Article;
import com.gmail.marozalena.onlinemarket.repository.model.Comment;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.converter.ArticleConverter;
import com.gmail.marozalena.onlinemarket.service.converter.CommentConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ArticleConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.CommentConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ProfileConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.RoleConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.UserConverterImpl;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.CommentDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Date;

public class ArticleConverterTest {

    private ArticleConverter articleConverter;

    @Before
    public void init() {
        articleConverter = new ArticleConverterImpl(
                new UserConverterImpl(
                        new RoleConverterImpl(), new ProfileConverterImpl()),
                new CommentConverterImpl(new UserConverterImpl(
                        new RoleConverterImpl(), new ProfileConverterImpl())));
    }

    @Test
    public void shouldConvertArticleDTOWithIdToArticle() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(2L);
        articleDTO.setDate("2019-10-13");
        articleDTO.setBody("Body");
        articleDTO.setPicture("image/test.jpg");
        CommentDTO commentDTO = new CommentDTO();
        articleDTO.setComments(Collections.singletonList(commentDTO));
        UserDTO userDTO = new UserDTO();
        articleDTO.setUser(userDTO);
        Article article = articleConverter.fromDTO(articleDTO);
        Assert.assertEquals(articleDTO.getId(), article.getId());
    }

    @Test
    public void shouldConvertArticleWithIdToArticleDTO() {
        Article article = new Article();
        article.setId(2L);
        article.setDate(new Date());
        article.setBody("Body");
        article.setPicture("image/test.jpg");
        Comment comment = new Comment();
        article.setComments(Collections.singletonList(comment));
        User user = new User();
        article.setUser(user);
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        Assert.assertEquals(articleDTO.getId(), article.getId());
    }
}
