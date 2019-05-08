package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.exception.UserNotAddedException;
import com.gmail.marozalena.onlinemarket.service.exception.UserNotFoundException;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDTO> getUsers() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.getUsers(connection);
                List<UserDTO> list = users.stream()
                        .map(userConverter::toUserDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return list;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserNotFoundException("Users not found in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserNotFoundException("Users not found in database", e);
        }
    }

    @Override
    public UserDTO loadUserByUsername(String email) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.loadUserByEmail(connection, email);
                UserDTO userDTO = userConverter.toUserDTO(user);
                connection.commit();
                return userDTO;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserNotFoundException("User with email " + email + " not found", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserNotFoundException("User with email " + email + " not found", e);
        }
    }

    @Override
    public void addUser(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try{
                User user = userConverter.fromUserDTO(userDTO);
                userRepository.addUser(connection, user);
                connection.commit();
            }catch (Exception e){
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserNotAddedException("User not added in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserNotAddedException("User not added in database", e);
        }
    }
}
