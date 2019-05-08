package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
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
    public List<User> getUsers(Connection connection) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM `users`,`roles` WHERE `users`.`roles_id`=`roles`.`id`";
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
        String sql = String.format("INSERT INTO `users` (`surname`,`name`,`patronymic`,`email`,`password`,`roles_id)" +
                        "VALUES ('%s','%s','%s','%s','%s','%d'", user.getSurname(), user.getName(), user.getPatronymic(),
                user.getEmail(), encoder(user.getPassword()), user.getRole().getId());
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new DatabaseException("Problems with adding user from database", e);
        }
    }

    private String encoder(String password) {
        return passwordEncoder.encode(password);
    }
}
