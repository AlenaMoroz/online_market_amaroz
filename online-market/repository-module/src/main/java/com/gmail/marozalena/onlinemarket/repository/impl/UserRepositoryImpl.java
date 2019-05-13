package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepositoryImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers(Connection connection, Integer page) {
        List<User> users = new ArrayList<>();
        String sql = String.format("SELECT * FROM `users`,`roles` WHERE `users`.`roles_id`=`roles`.`id` AND `deleted`='0'" +
                "LIMIT 10 OFFSET %d", (page-1)*10);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setSurname(resultSet.getString("surname"));
                user.setName(resultSet.getString("name"));
                user.setPatronymic(resultSet.getString("patronymic"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(new Role(resultSet.getLong("roles_id"),
                        resultSet.getString("role")));
                user.setDeleted(resultSet.getBoolean("deleted"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting users from database", e);
        }
        return users;
    }

    @Override
    public User loadUserByEmail(Connection connection, String email) {
        User user = new User();
        String sql = String.format("SELECT * FROM `users`,`roles` WHERE `users`.`email`='%s' " +
                "AND `users`.`roles_id`=`roles`.`id`", email);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setSurname(resultSet.getString("surname"));
                user.setName(resultSet.getString("name"));
                user.setPatronymic(resultSet.getString("patronymic"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(new Role(resultSet.getLong("roles_id"),
                        resultSet.getString("role")));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting user from database", e);
        }
        return user;
    }

    @Override
    public void addUser(Connection connection, User user) {
        String sql = String.format("INSERT INTO `users` (`surname`,`name`,`patronymic`,`email`,`password`,`roles_id`,`deleted`)" +
                        " VALUES ('%s','%s','%s','%s','%s','%d','0')",
                user.getSurname(),
                user.getName(),
                user.getPatronymic(),
                user.getEmail(),
                encoder(user.getPassword()),
                user.getRole().getId());
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with adding user from database", e);
        }
    }

    @Override
    public void deleteUsers(Connection connection, List<Long> idUsers) {
        for (Long idUser : idUsers) {
            String sql = String.format("UPDATE `users` SET `deleted`='1' WHERE `id`='%d'", idUser);
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new DatabaseException("Problems removing user from the database", e);
            }
        }
    }

    @Override
    public void saveUser(Connection connection, User user) {
        String sql = String.format("UPDATE `users` SET `roles_id`='%d' WHERE `email`='%s'",
                user.getRole().getId(), user.getEmail());
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with saving user from database", e);
        }
    }

    @Override
    public int countOfUsers(Connection connection) {
        String sql = "SELECT COUNT(*) FROM `users` WHERE deleted = '0'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting user from database", e);
        }
        return 0;
    }

    private String encoder(String password) {
        return passwordEncoder.encode(password);
    }

}
