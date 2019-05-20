package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import com.gmail.marozalena.onlinemarket.service.converter.ProfileConverter;
import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverterImpl implements ProfileConverter {

    @Override
    public Profile fromDTO(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setId(profileDTO.getId());
        profile.setName(profileDTO.getName());
        profile.setSurname(profileDTO.getSurname());
        profile.setPatronymic(profileDTO.getPatronymic());
        profile.setAddress(profileDTO.getAddress());
        profile.setPhone(profileDTO.getPhone());
        return profile;
    }

    @Override
    public ProfileDTO toDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setSurname(profile.getSurname());
        profileDTO.setPatronymic(profile.getPatronymic());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setPhone(profile.getPhone());
        return profileDTO;
    }
}
