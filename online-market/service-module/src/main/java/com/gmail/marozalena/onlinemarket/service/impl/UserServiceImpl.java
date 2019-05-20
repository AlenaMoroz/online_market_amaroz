package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ProfileRepository;
import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.RandomPasswordService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.exception.UserNotAddedException;
import com.gmail.marozalena.onlinemarket.service.exception.UserNotSavedException;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RandomPasswordService randomPasswordService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           RandomPasswordService randomPasswordService,
                           PasswordEncoder passwordEncoder,
                           ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.randomPasswordService = randomPasswordService;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public PageDTO<UserDTO> getUsers(Integer page) {
        List<User> users = userRepository.findAll(page);
        List<UserDTO> list = users.stream()
                .map(userConverter::toUserDTO)
                .sorted(Comparator.comparing(UserDTO::getEmail))
                .collect(Collectors.toList());
        PageDTO<UserDTO> listOfUsers = new PageDTO<>();
        listOfUsers.setList(list);
        listOfUsers.setCountOfPages(getCountOfPages(userRepository.getCountOfEntities()));
        return listOfUsers;
    }

    @Override
    @Transactional
    public UserDTO loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userConverter.toUserDTO(user);
    }

    @Override
    public void addUser(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                String randomPassword = randomPasswordService.getRandomPassword();
                userDTO.setPassword(randomPassword);
                logger.info("For new user was generated password: " + randomPassword);
                User user = userConverter.fromUserDTO(userDTO);
                profileRepository.addProfile(connection, user.getProfile());
                userRepository.addUser(connection, user);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserNotAddedException("User not added in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserNotAddedException("User not added in database", e);
        }
    }

    @Override
    @Transactional
    public void deleteUsers(List<Long> idUsers) {
        for (Long id : idUsers) {
            User user = userRepository.findByID(id);
            userRepository.remove(user);
        }
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userConverter.fromUserDTO(userDTO);
                userRepository.saveUser(connection, user);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserNotSavedException("Users not saved in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserNotSavedException("Users not saved in database", e);
        }
    }

    private int getCountOfPages(int countOfUsers) {
        int countOfPages = countOfUsers / LimitConstants.ENTITY_ON_PAGE;
        if (countOfUsers > (countOfPages * LimitConstants.ENTITY_ON_PAGE)) {
            countOfPages += 1;
        }
        return countOfPages;
    }

    private String encoder(String password) {
        return passwordEncoder.encode(password);
    }
}
