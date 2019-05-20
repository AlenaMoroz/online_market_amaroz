package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.CommentRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment>
        implements CommentRepository {
}
