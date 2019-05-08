package com.gmail.marozalena.onlinemarket.repository;

import com.gmail.marozalena.onlinemarket.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository{
    List<User> getUsers(Connection connection);

    User loadUserByEmail(Connection connection, String email);

    void addUser(Connection connection, User user);
}
