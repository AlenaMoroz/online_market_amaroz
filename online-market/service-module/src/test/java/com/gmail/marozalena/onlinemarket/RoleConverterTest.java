package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.service.converter.RoleConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.RoleConverterImpl;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RoleConverterTest {

    private RoleConverter roleConverter;

    @Before
    public void init(){
        roleConverter = new RoleConverterImpl();
    }

    @Test
    public  void shouldConvertRoleDTOWithIdToRole(){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        Role role = roleConverter.fromRoleDTO(roleDTO);
        Assert.assertEquals(roleDTO.getId(), role.getId());
    }

    @Test
    public  void shouldConvertRoleDTOWithNameToRole(){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole("Name");
        Role role = roleConverter.fromRoleDTO(roleDTO);
        Assert.assertEquals(roleDTO.getRole(), role.getRole());
    }

    @Test
    public  void shouldConvertRoleWithIdToRoleDTO(){
        Role role = new Role();
        role.setId(1L);
        RoleDTO roleDTO = roleConverter.toRoleDTO(role);
        Assert.assertEquals(role.getId(), roleDTO.getId());
    }

    @Test
    public  void shouldConvertRoleWithNameToRoleDTO(){
        Role role = new Role();
        role.setRole("Name");
        RoleDTO roleDTO = roleConverter.toRoleDTO(role);
        Assert.assertEquals(role.getRole(), roleDTO.getRole());
    }
}
