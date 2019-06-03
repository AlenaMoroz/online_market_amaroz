package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.OrderRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Item;
import com.gmail.marozalena.onlinemarket.repository.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public List<Order> findAll(Integer page) {
        String query = "FROM " + entityClass.getName() + " ORDER BY date DESC";
        Query q = entityManager.createQuery(query)
                .setFirstResult(getCountOfOffset(page))
                .setMaxResults(LimitConstants.ENTITY_ON_PAGE);
        return q.getResultList();
    }

    @Override
    public List<Order> findOrdersForUser(Integer page, Long id) {
        String hql = "FROM Order as O WHERE O.user.id=:idParam ORDER BY date DESC";
        Query q = entityManager.createQuery(hql)
                .setFirstResult(getCountOfOffset(page))
                .setMaxResults(LimitConstants.ENTITY_ON_PAGE);
        q.setParameter("idParam", id);
        return q.getResultList();
    }
}
