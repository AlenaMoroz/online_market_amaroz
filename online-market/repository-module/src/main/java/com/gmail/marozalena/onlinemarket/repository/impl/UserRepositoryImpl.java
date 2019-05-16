package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.repository.pagination.PaginationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants.USERS_ON_PAGE;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final PaginationRepository paginationRepository;

    @Autowired
    public UserRepositoryImpl(
            PasswordEncoder passwordEncoder,
            PaginationRepository paginationRepository)
    {
        this.passwordEncoder = passwordEncoder;
        this.paginationRepository = paginationRepository;
    }

    @Override
    public List<User> getUsers(Connection connection, Integer page) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM `users`,`roles` WHERE `users`.`roles_id`=`roles`.`id` AND `deleted`='0'" +
                "LIMIT ? OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, USERS_ON_PAGE);
            preparedStatement.setInt(2, paginationRepository.getCountOfOffset(page));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setName(resultSet.getString("name"));
                    user.setPatronymic(resultSet.getString("patronymic"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    Role role = new Role();
                    role.setId(resultSet.getLong("roles_id"));
                    role.setName(resultSet.getString("role"));
                    user.setRole(role);
                    users.add(user);
                }
            }catch (Exception e){
                logger.error(e.getMessage(), e);
                throw new DatabaseException("Problems with getting users from database", e);
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
        String sql = "SELECT * FROM `users`,`roles` WHERE `users`.`email`=? " +
                "AND `users`.`roles_id`=`roles`.`id`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting user from database", e);
        }
        return user;
    }

    @Override
    public void addUser(Connection connection, User user) {
        String sql = "INSERT INTO users (surname, name, patronymic, email, password, roles_id, deleted)" +
                " VALUES (?, ?, ?, ?, ?, ?, '0')";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPatronymic());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, encoder(user.getPassword()));
            preparedStatement.setLong(6, user.getRole().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with adding user from database", e);
        }
    }

    @Override
    public void deleteUsers(Connection connection, List<Long> idUsers) {
        for (Long idUser : idUsers) {
            String sql = "UPDATE users SET deleted='1' WHERE `id`=?";
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
    }

    private String encoder(String password) {
        return passwordEncoder.encode(password);
    }

}
