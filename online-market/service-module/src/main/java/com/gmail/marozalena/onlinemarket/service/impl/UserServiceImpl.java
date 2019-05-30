package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.RoleRepository;
import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.RandomPasswordService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RandomPasswordService randomPasswordService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           RandomPasswordService randomPasswordService,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.randomPasswordService = randomPasswordService;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public PageDTO<UserDTO> getUsers(Integer page) {
        List<User> users = userRepository.findUsers(page, "email");
        List<UserDTO> list = users.stream()
                .map(userConverter::toUserDTO)
               // .sorted(Comparator.comparing(UserDTO::getEmail))
                .collect(Collectors.toList());
        PageDTO<UserDTO> listOfUsers = new PageDTO<>();
        listOfUsers.setList(list);
        listOfUsers.setCountOfPages(getCountOfPages(userRepository.getCountOfEntities()));
        return listOfUsers;
    }

    @Override
    @Transactional
    public UserDTO loadUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return userConverter.toUserDTO(user);
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) {
        Role role = roleRepository.findByID(userDTO.getRole().getId());
        User user = userConverter.fromUserDTO(userDTO);
        user.setRole(role);
        user.setPassword(randomPasswordService.getRandomPassword(userDTO.getEmail()));
        user.getProfile().setUser(user);
        userRepository.persist(user);
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
    @Transactional
    public void saveUser(UserDTO userDTO) {
        Role role = roleRepository.findByID(userDTO.getRole().getId());
        User user = userRepository.findByID(userDTO.getId());
        user.setRole(role);
        userRepository.merge(user);
    }

    private int getCountOfPages(int countOfUsers) {
        int countOfPages = countOfUsers / LimitConstants.ENTITY_ON_PAGE;
        if (countOfUsers > (countOfPages * LimitConstants.ENTITY_ON_PAGE)) {
            countOfPages += 1;
        }
        return countOfPages;
    }
}
