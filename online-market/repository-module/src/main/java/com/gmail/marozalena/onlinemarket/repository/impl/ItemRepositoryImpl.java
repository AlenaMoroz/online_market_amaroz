package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ItemRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item>
        implements ItemRepository {

    @Override
    @SuppressWarnings({"uncheked", "rawtypes"})
    public List<Item> findAll(Integer page) {
        String query = "FROM " + entityClass.getName() + " WHERE deleted = '0' ORDER BY name";
        Query q = entityManager.createQuery(query)
                .setFirstResult(getCountOfOffset(page))
                .setMaxResults(LimitConstants.ENTITY_ON_PAGE);
        return q.getResultList();
    }

    @Override
    public Item findByNumber(String number) {
        String query = "FROM " + entityClass.getName() + " WHERE number = :numberParam";
        Query q = entityManager.createQuery(query);
        q.setParameter("numberParam", number);
        return (Item) q.getSingleResult();
    }
}
