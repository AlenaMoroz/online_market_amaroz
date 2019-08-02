package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User findUserByEmail(String email) {
        String query = "FROM " + entityClass.getName() + " WHERE email = :emailParam AND deleted = '0'";
        Query q = entityManager.createQuery(query);
        q.setParameter("emailParam", email);
        return (User) q.getSingleResult();
    }

    @Override
    @SuppressWarnings({"uncheked", "rawtypes"})
    public List<User> findAll(Integer page) {
        String query = "FROM " + entityClass.getName() + " WHERE deleted = '0' ORDER BY email";
        Query q = entityManager.createQuery(query)
                .setFirstResult(getCountOfOffset(page))
                .setMaxResults(LimitConstants.ENTITY_ON_PAGE);
        return q.getResultList();
    }
}
