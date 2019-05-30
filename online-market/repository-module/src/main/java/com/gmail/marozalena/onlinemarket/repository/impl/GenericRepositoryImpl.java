package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.GenericRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {

    private static final Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);

    protected Class<T> entityClass;
    @PersistenceContext
    protected EntityManager entityManager;
    @Autowired
    private DataSource dataSource;

    @SuppressWarnings(value = "uncheked")
    public GenericRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with connection", e);
        }
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void merge(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T findByID(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    @SuppressWarnings({"uncheked", "rawtypes"})
    public List<T> findAll(Integer page) {
        String query = "FROM " + entityClass.getName();
        Query q = entityManager.createQuery(query)
                .setFirstResult(getCountOfOffset(page))
                .setMaxResults(LimitConstants.ENTITY_ON_PAGE);
        return q.getResultList();
    }

    @Override
    @SuppressWarnings({"uncheked", "rawtypes"})
    public List<T> findAll() {
        String query = "FROM " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query);
        return q.getResultList();
    }

    @Override
    public int getCountOfEntities() {
        String query = "SELECT COUNT(*) FROM " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }

    public int getCountOfOffset(Integer page) {
        return (page - 1) * 10;
    }

}
