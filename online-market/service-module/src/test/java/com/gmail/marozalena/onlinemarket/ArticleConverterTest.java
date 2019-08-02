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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import static com.gmail.marozalena.onlinemarket.service.constant.DateConstants.PATTERN_FOR_DATE;

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
        Article article = articleConverter.fromDTO(articleDTO);
        Assert.assertEquals(articleDTO.getId(), article.getId());
    }

    @Test
    public void shouldConvertArticleDTOWithDateToArticle() throws ParseException {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setDate("2019-10-13");
        Article article = articleConverter.fromDTO(articleDTO);
        Assert.assertEquals(new SimpleDateFormat(PATTERN_FOR_DATE).parse(articleDTO.getDate()),
                article.getDate());
    }

    @Test
    public void shouldConvertArticleDTOWithBodyToArticle() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setBody("Body");
        Article article = articleConverter.fromDTO(articleDTO);
        Assert.assertEquals(articleDTO.getBody(), article.getBody());
    }

    @Test
    public void shouldConvertArticleDTOWithImageToArticle() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setPicture("image/test.jpg");
        Article article = articleConverter.fromDTO(articleDTO);
        Assert.assertEquals(articleDTO.getPicture(), article.getPicture());
    }

    @Test
    public void shouldConvertArticleDTOWithCommentsToArticle() {
        ArticleDTO articleDTO = new ArticleDTO();
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("Test Comment");
        articleDTO.setComments(Collections.singletonList(commentDTO));
        Article article = articleConverter.fromDTO(articleDTO);
        Assert.assertEquals(articleDTO.getComments().get(0).getComment(),
                article.getComments().get(0).getComment());
    }

    @Test
    public void shouldConvertArticleDTOWithUserToArticle() {
        ArticleDTO articleDTO = new ArticleDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        articleDTO.setUser(userDTO);
        Article article = articleConverter.fromDTO(articleDTO);
        Assert.assertEquals(articleDTO.getUser().getEmail(), article.getUser().getEmail());
    }

    @Test
    public void shouldConvertArticleWithIdToArticleDTO() {
        Article article = new Article();
        article.setId(2L);
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        Assert.assertEquals(articleDTO.getId(), article.getId());
    }

    @Test
    public void shouldConvertArticleWithDateToArticleDTO() {
        Article article = new Article();
        article.setDate(new Date());
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        Assert.assertEquals(articleDTO.getDate(),
                new SimpleDateFormat(PATTERN_FOR_DATE).format(article.getDate()));
    }

    @Test
    public void shouldConvertArticleWithBodyToArticleDTO() {
        Article article = new Article();
        article.setBody("Body");
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        Assert.assertEquals(articleDTO.getBody(), article.getBody());
    }

    @Test
    public void shouldConvertArticleWithPictureToArticleDTO() {
        Article article = new Article();
        article.setPicture("image/test.jpg");
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        Assert.assertEquals(articleDTO.getPicture(), article.getPicture());
    }

    @Test
    public void shouldConvertArticleWithCommentsToArticleDTO() {
        Article article = new Article();
        Comment comment = new Comment();
        comment.setComment("Test Comment");
        article.setComments(Collections.singletonList(comment));
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        Assert.assertEquals(articleDTO.getComments().get(0).getComment(),
                article.getComments().get(0).getComment());
    }

    @Test
    public void shouldConvertArticleWithUserToArticleDTO() {
        Article article = new Article();
        User user = new User();
        user.setEmail("test@gmail.com");
        article.setUser(user);
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        Assert.assertEquals(articleDTO.getUser().getEmail(), article.getUser().getEmail());
    }
}
