package com.gmail.marozalena.onlinemarket.converter;

import com.gmail.marozalena.onlinemarket.model.Role;
import com.gmail.marozalena.onlinemarket.model.RoleDTO;

public interface RoleConverter {

    Role fromRoleDTO(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);
}
