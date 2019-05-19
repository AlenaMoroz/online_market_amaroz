package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ProfileRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import com.gmail.marozalena.onlinemarket.service.ProfileService;
import com.gmail.marozalena.onlinemarket.service.converter.ProfileConverter;
import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository,
                              ProfileConverter profileConverter) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
    }

    @Override
    @Transactional
    public ProfileDTO getProfile(Long id) {
        Profile profile = profileRepository.findByID(id);
        return profileConverter.toDTO(profile);
    }

    @Override
    @Transactional
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        Profile profile = profileRepository.findByID(profileDTO.getId());
        profile.setName(profileDTO.getName());
        profile.setSurname(profileDTO.getSurname());
        profile.setAddress(profileDTO.getAddress());
        profile.setPhone(profileDTO.getPhone());
        //TODO with User!
        profileRepository.persist(profile);
        return profileConverter.toDTO(profile);
    }
}
