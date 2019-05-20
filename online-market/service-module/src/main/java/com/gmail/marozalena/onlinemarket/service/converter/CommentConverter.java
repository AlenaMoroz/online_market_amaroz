package com.gmail.marozalena.onlinemarket.service.converter;

import com.gmail.marozalena.onlinemarket.repository.model.Comment;
import com.gmail.marozalena.onlinemarket.service.model.CommentDTO;

import java.util.List;

public interface CommentConverter {

    List<Comment> fromDTO(List<CommentDTO> comments);

    List<CommentDTO> toDTO(List<Comment> comments);
}
