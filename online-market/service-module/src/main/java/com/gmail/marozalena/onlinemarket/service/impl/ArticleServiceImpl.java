package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ArticleRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Article;
import com.gmail.marozalena.onlinemarket.service.ArticleService;
import com.gmail.marozalena.onlinemarket.service.converter.ArticleConverter;
import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
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
        article.setId(articleDTO.getId());
        articleRepository.persist(article);
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findByID(id);
        articleRepository.remove(article);
    }

    private int getCountOfPages(int countOfArticles) {
        int countOfPages = countOfArticles / LimitConstants.ENTITY_ON_PAGE;
        if (countOfArticles > (countOfPages * LimitConstants.ENTITY_ON_PAGE)) {
            countOfPages += 1;
        }
        return countOfPages;
    }
}
