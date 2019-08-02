package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Comment;
import com.gmail.marozalena.onlinemarket.service.converter.CommentConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.model.CommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gmail.marozalena.onlinemarket.service.constant.DateConstants.PATTERN_FOR_DATE;

@Component
public class CommentConverterImpl implements CommentConverter {

    private static final Logger logger = LoggerFactory.getLogger(CommentConverterImpl.class);

    private final UserConverter userConverter;

    @Autowired
    public CommentConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public List<Comment> fromDTO(List<CommentDTO> comments) {
        List<Comment> list = new ArrayList<>();
        for (CommentDTO commentDTO : comments) {
            Comment comment = new Comment();
            comment.setId(commentDTO.getId());
            try {
                if (commentDTO.getDate() != null) {
                    comment.setDate(new SimpleDateFormat(PATTERN_FOR_DATE).parse(commentDTO.getDate()));
                } else {
                    comment.setDate(new Date());
                }
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
                comment.setDate(new Date());
            }
            comment.setUser(userConverter.fromUserDTO(commentDTO.getUser()));
            comment.setComment(commentDTO.getComment());
            list.add(comment);
        }
        return list;
    }

    @Override
    public List<CommentDTO> toDTO(List<Comment> comments) {
        List<CommentDTO> list = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setComment(comment.getComment());
            commentDTO.setUser(userConverter.toUserDTO(comment.getUser()));
            if (comment.getDate() != null) {
                commentDTO.setDate(new SimpleDateFormat(PATTERN_FOR_DATE).format(comment.getDate()));
            }
            list.add(commentDTO);
        }
        return list;
    }
}
