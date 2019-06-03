package com.gmail.marozalena.onlinemarket.repository;

import com.gmail.marozalena.onlinemarket.repository.model.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item>{

    List<Item> findAll(Integer page);

    Item findByNumber(String number);
}
