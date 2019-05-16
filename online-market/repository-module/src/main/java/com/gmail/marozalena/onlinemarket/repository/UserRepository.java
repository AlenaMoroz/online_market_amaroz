package com.gmail.marozalena.onlinemarket.repository;

import com.gmail.marozalena.onlinemarket.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository{
    List<User> getUsers(Connection connection, Integer page);

    User loadUserByEmail(Connection connection, String email);

    void addUser(Connection connection, User user);

    void deleteUsers(Connection connection, List<Long> idUsers);

    void saveUser(Connection connection, User user);

    int getCountOfUsers(Connection connection);
}
