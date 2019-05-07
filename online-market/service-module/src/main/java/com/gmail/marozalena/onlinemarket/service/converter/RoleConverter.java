package com.gmail.marozalena.onlinemarket.service.converter;

import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;

public interface RoleConverter {

    Role fromRoleDTO(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);
}
