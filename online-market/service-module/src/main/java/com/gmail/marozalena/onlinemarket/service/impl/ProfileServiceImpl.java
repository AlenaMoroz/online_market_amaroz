package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ProfileRepository;
import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.ProfileService;
import com.gmail.marozalena.onlinemarket.service.converter.ProfileConverter;
import com.gmail.marozalena.onlinemarket.service.exception.ServiceException;
import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository,
                              ProfileConverter profileConverter,
                              UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ProfileDTO getProfile(Long id) {
        Profile profile = profileRepository.findByID(id);
        return profileConverter.toDTO(profile);
    }

    @Override
    @Transactional
    public ProfileDTO updateProfile(ProfileDTO profileDTO,
                                    String password,
                                    String newPassword) {
        Profile profile = profileRepository.findByID(profileDTO.getId());
        if (password.isEmpty() && newPassword.isEmpty()) {
            profile.setName(profileDTO.getName());
            profile.setSurname(profileDTO.getSurname());
            profile.setAddress(profileDTO.getAddress());
            profile.setPhone(profileDTO.getPhone());
            profileRepository.persist(profile);
            return profileConverter.toDTO(profile);
        } else {
            User user = userRepository.findByID(profileDTO.getId());
            if (passwordEncoder.matches(password, user.getPassword()) && !newPassword.isEmpty()) {
                user.getProfile().setName(profileDTO.getName());
                user.getProfile().setSurname(profileDTO.getSurname());
                user.getProfile().setAddress(profileDTO.getAddress());
                user.getProfile().setPhone(profileDTO.getPhone());
                user.setPassword(encoder(newPassword));
                userRepository.merge(user);
                return profileConverter.toDTO(user.getProfile());
            } else throw new ServiceException("Not valid password or empty new password");

        }
    }

    private String encoder(String password) {
        return passwordEncoder.encode(password);
    }
}
