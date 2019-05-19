package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ProfileRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepositoryImpl extends GenericRepositoryImpl<Long, Profile>
        implements ProfileRepository {
}
