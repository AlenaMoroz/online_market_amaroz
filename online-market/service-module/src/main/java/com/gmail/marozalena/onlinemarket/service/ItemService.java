package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.ItemDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> findItems();

    ItemDTO findByID(Long id);

    void addItem(ItemDTO itemDTO);

    void deleteItem(Long id);

    PageDTO<ItemDTO> findItems(Integer page);

    void copyItem(Long id);
}
