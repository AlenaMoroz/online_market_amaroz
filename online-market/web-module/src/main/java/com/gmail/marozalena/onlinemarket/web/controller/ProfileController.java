package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ProfileService;
import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Authentication authentication) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        ProfileDTO profileDTO = profileService.getProfile(userDTO.getId());
        model.addAttribute("profile", profileDTO);
        return "profile";
    }

    @GetMapping("/profile/save")
    public String updateProfile(Model model, ProfileDTO profileDTO) {
        ProfileDTO profile = profileService.updateProfile(profileDTO);
        model.addAttribute("profile", profile);
        return "profile";
    }
}
