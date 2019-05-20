package com.gmail.marozalena.onlinemarket.service.converter;

import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;

public interface ProfileConverter {

    Profile fromDTO(ProfileDTO profileDTO);
    ProfileDTO toDTO(Profile profile);
}
