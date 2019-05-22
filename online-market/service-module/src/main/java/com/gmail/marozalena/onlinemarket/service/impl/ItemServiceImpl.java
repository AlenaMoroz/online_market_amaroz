package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ItemRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Item;
import com.gmail.marozalena.onlinemarket.service.ItemService;
import com.gmail.marozalena.onlinemarket.service.converter.ItemConverter;
import com.gmail.marozalena.onlinemarket.service.model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    public List<ItemDTO> findItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(itemConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDTO findByID(Long id) {
        Item item = itemRepository.findByID(id);
        return itemConverter.toDTO(item);
    }

    @Override
    public void addItem(ItemDTO itemDTO) {
        Item item = itemConverter.fromDTO(itemDTO);
        itemRepository.persist(item);
    }

    @Override
    public void deleteItem(Long id) {
        Item item = itemRepository.findByID(id);
        itemRepository.persist(item);
    }
}
