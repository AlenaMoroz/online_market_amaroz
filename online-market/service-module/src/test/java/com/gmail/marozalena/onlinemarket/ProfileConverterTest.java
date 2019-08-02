package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import com.gmail.marozalena.onlinemarket.service.converter.ProfileConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ProfileConverterImpl;
import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProfileConverterTest {

    private ProfileConverter profileConverter;

    @Before
    public void init() {
        profileConverter = new ProfileConverterImpl();
    }

    @Test
    public void shouldConvertProfileDTOWithIdToProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(2L);
        Profile profile = profileConverter.fromDTO(profileDTO);
        Assert.assertEquals(profileDTO.getId(), profile.getId());
    }

    @Test
    public void shouldConvertProfileDTOWithNameToProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName("Name");
        Profile profile = profileConverter.fromDTO(profileDTO);
        Assert.assertEquals(profileDTO.getName(), profile.getName());
    }

    @Test
    public void shouldConvertProfileDTOWithSurnameToProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setSurname("Surname");
        Profile profile = profileConverter.fromDTO(profileDTO);
        Assert.assertEquals(profileDTO.getSurname(), profile.getSurname());
    }

    @Test
    public void shouldConvertProfileDTOWithPatronymicToProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setPatronymic("Patronymic");
        Profile profile = profileConverter.fromDTO(profileDTO);
        Assert.assertEquals(profileDTO.getPatronymic(), profile.getPatronymic());
    }
    @Test
    public void shouldConvertProfileDTOWithAddressToProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAddress("Address");
        Profile profile = profileConverter.fromDTO(profileDTO);
        Assert.assertEquals(profileDTO.getAddress(), profile.getAddress());
    }

    @Test
    public void shouldConvertProfileDTOWithPhoneToProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setPhone("+375292005674");
        Profile profile = profileConverter.fromDTO(profileDTO);
        Assert.assertEquals(profileDTO.getPhone(), profile.getPhone());
    }

    @Test
    public void shouldConvertProfileWithIdToProfileDTO() {
        Profile profile = new Profile();
        profile.setId(2L);
        ProfileDTO profileDTO = profileConverter.toDTO(profile);
        Assert.assertEquals(profile.getId(), profileDTO.getId());
    }

    @Test
    public void shouldConvertProfileWithNameToProfileDTO() {
        Profile profile = new Profile();
        profile.setName("Name");
        ProfileDTO profileDTO = profileConverter.toDTO(profile);
        Assert.assertEquals(profile.getName(), profileDTO.getName());
    }

    @Test
    public void shouldConvertProfileWithSurnameToProfileDTO() {
        Profile profile = new Profile();
        profile.setSurname("Surname");
        ProfileDTO profileDTO = profileConverter.toDTO(profile);
        Assert.assertEquals(profile.getSurname(), profileDTO.getSurname());
    }

    @Test
    public void shouldConvertProfileWithPatronymicToProfileDTO() {
        Profile profile = new Profile();
        profile.setPatronymic("Patronymic");
        ProfileDTO profileDTO = profileConverter.toDTO(profile);
        Assert.assertEquals(profile.getPatronymic(), profileDTO.getPatronymic());
    }

    @Test
    public void shouldConvertProfileWithAddressToProfileDTO() {
        Profile profile = new Profile();
        profile.setAddress("Address");
        ProfileDTO profileDTO = profileConverter.toDTO(profile);
        Assert.assertEquals(profile.getAddress(), profileDTO.getAddress());
    }

    @Test
    public void shouldConvertProfileWithPhoneToProfileDTO() {
        Profile profile = new Profile();
        profile.setPhone("+375292005674");
        ProfileDTO profileDTO = profileConverter.toDTO(profile);
        Assert.assertEquals(profile.getPhone(), profileDTO.getPhone());
    }
}
