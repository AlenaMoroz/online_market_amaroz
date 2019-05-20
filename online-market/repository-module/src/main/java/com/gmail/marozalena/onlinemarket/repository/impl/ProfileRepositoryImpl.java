package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ProfileRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ProfileRepositoryImpl extends GenericRepositoryImpl<Long, Profile>
        implements ProfileRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProfileRepositoryImpl.class);

    @Override
    public void addProfile(Connection connection, Profile profile) {
        String sql = "INSERT INTO profiles (name, surname, patronymic)" +
                " VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setString(2, profile.getSurname());
            preparedStatement.setString(3, profile.getPatronymic());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with adding profile from database", e);
        }
    }
}
