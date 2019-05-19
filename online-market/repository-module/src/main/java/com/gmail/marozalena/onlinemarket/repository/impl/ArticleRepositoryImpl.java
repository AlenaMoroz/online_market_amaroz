package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ArticleRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Article;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article>
        implements ArticleRepository {
}
