package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    /*@Override
    public void deleteUsers(Connection connection, List<Long> idUsers) {
        for (Long idUser : idUsers) {
            String sql = "UPDATE users SET deleted = '1' WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idUser);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new DatabaseException("Problems removing user from the database", e);
            }
        }
    }

    @Override
    public void saveUser(Connection connection, User user) {
        String sql = "UPDATE users SET roles_id=? WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, user.getRole().getId());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with saving user from database", e);
        }
    }*/
}
