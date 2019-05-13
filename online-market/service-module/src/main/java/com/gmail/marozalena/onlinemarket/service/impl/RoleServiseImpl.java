package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.RoleRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.service.RoleServise;
import com.gmail.marozalena.onlinemarket.service.converter.RoleConverter;
import com.gmail.marozalena.onlinemarket.service.exception.RolesNotFoundException;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiseImpl implements RoleServise {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiseImpl.class);
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Autowired
    public RoleServiseImpl(RoleRepository roleRepository,
                           RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public List<RoleDTO> getRoles() {
        try(Connection connection = roleRepository.getConnection()){
            connection.setAutoCommit(false);
            try{
                List<Role> roles = roleRepository.getRoles(connection);
                List<RoleDTO> list = roles.stream().map(roleConverter::toRoleDTO).collect(Collectors.toList());
                connection.commit();
                return list;
            }catch (Exception e){
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new RolesNotFoundException("Roles not found in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RolesNotFoundException("Roles not found in database", e);
        }
    }
}
