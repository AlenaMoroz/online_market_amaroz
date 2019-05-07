package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.service.converter.RoleConverter;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;

public class RoleConverterImpl implements RoleConverter {

    @Override
    public Role fromRoleDTO(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        return role;
    }

    @Override
    public RoleDTO toRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
