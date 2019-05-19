package com.gmail.marozalena.onlinemarket.repository;

import com.gmail.marozalena.onlinemarket.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {

    //void deleteUsers(Connection connection, List<Long> idUsers);

    //void saveUser(Connection connection, User user);
}
