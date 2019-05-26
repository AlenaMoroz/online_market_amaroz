package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ArticleRepository;
import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Article;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.ArticleService;
import com.gmail.marozalena.onlinemarket.service.converter.ArticleConverter;
import com.gmail.marozalena.onlinemarket.service.exception.ServiceException;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;
    private final UserRepository userRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              ArticleConverter articleConverter,
                              UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public PageDTO<ArticleDTO> getArticles(Integer page) {
        List<Article> articles = articleRepository.findAll(page);
        List<ArticleDTO> list = articles.stream()
                .map(articleConverter::toDTO)
                .sorted(Comparator.comparing(ArticleDTO::getDate))
                .collect(Collectors.toList());
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        pageDTO.setList(list);
        pageDTO.setCountOfPages(
                getCountOfPages(articleRepository.getCountOfEntities()));
        return pageDTO;
    }

    @Override
    @Transactional
    public ArticleDTO findByID(Long id) {
        Article article = articleRepository.findByID(id);
        return articleConverter.toDTO(article);
    }

    @Override
    @Transactional
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(articleConverter::toDTO)
                .sorted(Comparator.comparing(ArticleDTO::getDate))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addArticle(ArticleDTO articleDTO) {
        Article article = articleConverter.fromDTO(articleDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findUserByEmail(email);
        article.setUser(user);
        articleRepository.persist(article);
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findByID(id);
        articleRepository.remove(article);
    }

    @Override
    @Transactional
    public void createArticle(ArticleDTO articleDTO, MultipartFile file) {
        uploadImage(file);
        articleDTO.setPicture("images/" + file.getOriginalFilename());
        Article article = articleConverter.fromDTO(articleDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findUserByEmail(email);
        article.setUser(user);
        articleRepository.persist(article);
    }

    @Override
    @Transactional
    public void updateArticle(ArticleDTO articleDTO) {
        Article article = articleRepository.findByID(articleDTO.getId());
        article.setTopic(articleDTO.getTopic());
        article.setBody(articleDTO.getBody());
        article.setDate(new Date());
        articleRepository.merge(article);
    }

    private void uploadImage(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(System.getProperty("user.dir")
                    + "/web-module/src/main/resources/templates/images/"
                    + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Unable to upload image", e);
        }
    }

    private int getCountOfPages(int countOfArticles) {
        int countOfPages = countOfArticles / LimitConstants.ENTITY_ON_PAGE;
        if (countOfArticles > (countOfPages * LimitConstants.ENTITY_ON_PAGE)) {
            countOfPages += 1;
        }
        return countOfPages;
    }
}
