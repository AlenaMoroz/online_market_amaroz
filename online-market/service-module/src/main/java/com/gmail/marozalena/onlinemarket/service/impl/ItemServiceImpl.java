package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ItemRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Item;
import com.gmail.marozalena.onlinemarket.service.ItemService;
import com.gmail.marozalena.onlinemarket.service.converter.ItemConverter;
import com.gmail.marozalena.onlinemarket.service.model.ItemDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
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
    @Transactional
    public List<ItemDTO> findItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(itemConverter::toDTO)
                .sorted(Comparator.comparing(ItemDTO::getName))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemDTO findByID(Long id) {
        Item item = itemRepository.findByID(id);
        return itemConverter.toDTO(item);
    }

    @Override
    @Transactional
    public void addItem(ItemDTO itemDTO) {
        Item item = itemConverter.fromDTO(itemDTO);
        item.setNumber(generateRandomNumber());
        itemRepository.persist(item);
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        Item item = itemRepository.findByID(id);
        itemRepository.remove(item);
    }

    @Override
    @Transactional
    public PageDTO<ItemDTO> findItems(Integer page) {
        List<Item> articles = itemRepository.findAll(page);
        List<ItemDTO> list = articles.stream()
                .map(itemConverter::toDTO)
                .collect(Collectors.toList());
        PageDTO<ItemDTO> pageDTO = new PageDTO<>();
        pageDTO.setList(list);
        pageDTO.setCountOfPages(
                getCountOfPages(itemRepository.getCountOfEntities()));
        return pageDTO;
    }

    @Override
    @Transactional
    public void copyItem(Long id) {
        Item item = itemRepository.findByID(id);
        Item item2 = new Item();
        item2.setName(item.getName()+"_2");
        item2.setNumber(generateRandomNumber());
        item2.setDescription(item.getDescription());
        item2.setPrice(item.getPrice());
        itemRepository.persist(item2);
    }

    private String generateRandomNumber() {
        UUID number = UUID.randomUUID();
        return number.toString();
    }

    private int getCountOfPages(int countOfItems) {
        int countOfPages = countOfItems / LimitConstants.ENTITY_ON_PAGE;
        if (countOfItems > (countOfPages * LimitConstants.ENTITY_ON_PAGE)) {
            countOfPages += 1;
        }
        return countOfPages;
    }
}
