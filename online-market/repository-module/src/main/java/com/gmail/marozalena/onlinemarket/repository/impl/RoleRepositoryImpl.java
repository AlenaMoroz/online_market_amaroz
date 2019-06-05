package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.RoleRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    @Override
    @SuppressWarnings({"uncheked", "rawtypes"})
    public List<Role> findAll() {
        String query = "FROM " + entityClass.getName();
        Query q = entityManager.createQuery(query);
        return q.getResultList();
    }

    @Override
    public Role findRoleByRoleName(String role) {
        String query = "FROM " + entityClass.getName() + " WHERE role = :roleParam";
        Query q = entityManager.createQuery(query);
        q.setParameter("roleParam", role);
        return (Role) q.getSingleResult();
    }

}
