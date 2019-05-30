package com.gmail.marozalena.onlinemarket.repository;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<I, T> {

    Connection getConnection();

    void persist(T entity);

    void merge(T entity);

    void remove(T entity);

    T findByID(I id);

    List<T> findAll(Integer page, String param);

    List<T> findAll();

    int getCountOfEntities();
}
