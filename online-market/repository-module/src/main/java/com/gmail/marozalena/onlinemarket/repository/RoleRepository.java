package com.gmail.marozalena.onlinemarket.repository;

import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface RoleRepository extends GenericRepository<Long, Role> {

    Role findRoleByRoleName(String role);

}
