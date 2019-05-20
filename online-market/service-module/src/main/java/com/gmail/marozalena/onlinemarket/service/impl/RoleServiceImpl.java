package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.RoleRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.service.RoleService;
import com.gmail.marozalena.onlinemarket.service.converter.RoleConverter;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public List<RoleDTO> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleConverter::toRoleDTO)
                .collect(Collectors.toList());
    }
}
