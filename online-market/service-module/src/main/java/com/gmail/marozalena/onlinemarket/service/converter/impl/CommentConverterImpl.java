package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Comment;
import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import com.gmail.marozalena.onlinemarket.service.converter.CommentConverter;
import com.gmail.marozalena.onlinemarket.service.model.CommentDTO;
import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements CommentConverter {
    @Override
    public Comment fromDTO(ProfileDTO profileDTO) {
        return null;
    }

    @Override
    public CommentDTO toDTO(Profile profile) {
        return null;
    }
}
