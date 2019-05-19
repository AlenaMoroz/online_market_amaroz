package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;

public interface ProfileService {
    ProfileDTO getProfile(Long id);

    ProfileDTO updateProfile(ProfileDTO profileDTO);
}
