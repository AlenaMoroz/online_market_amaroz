package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Comment;
import com.gmail.marozalena.onlinemarket.service.converter.CommentConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.model.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentConverterImpl implements CommentConverter {

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
            comment.setDate(commentDTO.getDate());
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
            commentDTO.setDate(comment.getDate());
            list.add(commentDTO);
        }
        return list;
    }
}
