package com.gmail.marozalena.onlinemarket.service.converter;

import com.gmail.marozalena.onlinemarket.repository.model.Comment;
import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import com.gmail.marozalena.onlinemarket.service.model.CommentDTO;
import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;

public interface CommentConverter {

    Comment fromDTO(ProfileDTO profileDTO);
    CommentDTO toDTO(Profile profile);
}
