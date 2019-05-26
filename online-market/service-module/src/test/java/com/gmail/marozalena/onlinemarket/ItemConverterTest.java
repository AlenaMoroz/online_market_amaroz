package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.repository.model.Item;
import com.gmail.marozalena.onlinemarket.service.converter.ItemConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ItemConverterImpl;
import com.gmail.marozalena.onlinemarket.service.model.ItemDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemConverterTest {

    private ItemConverter itemConverter;

    @Before
    public void init() {
        itemConverter = new ItemConverterImpl();
    }

    @Test
    public void shouldConvertItemDTOWithIdToItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(2L);
        Item item = itemConverter.fromDTO(itemDTO);
        Assert.assertEquals(itemDTO.getId(), item.getId());
    }

    @Test
    public void shouldConvertItemDTOWithNameToItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("Name");
        Item item = itemConverter.fromDTO(itemDTO);
        Assert.assertEquals(itemDTO.getName(), item.getName());
    }

    @Test
    public void shouldConvertItemDTOWithNumberToItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setNumber("213767842084");
        Item item = itemConverter.fromDTO(itemDTO);
        Assert.assertEquals(itemDTO.getNumber(), item.getNumber());
    }

    @Test
    public void shouldConvertItemDTOWithDescriptionToItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescription("Description");
        Item item = itemConverter.fromDTO(itemDTO);
        Assert.assertEquals(itemDTO.getDescription(), item.getDescription());
    }

    @Test
    public void shouldConvertItemDTOWithPriceToItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setPrice(35.48);
        Item item = itemConverter.fromDTO(itemDTO);
        Assert.assertEquals(itemDTO.getPrice(), item.getPrice());
    }

    @Test
    public void shouldConvertItemWithIdToItemDTO() {
        Item item = new Item();
        item.setId(2L);
        ItemDTO itemDTO = itemConverter.toDTO(item);
        Assert.assertEquals(item.getId(), itemDTO.getId());
    }

    @Test
    public void shouldConvertItemWithNameToItemDTO() {
        Item item = new Item();
        item.setName("Name");
        ItemDTO itemDTO = itemConverter.toDTO(item);
        Assert.assertEquals(item.getName(), itemDTO.getName());
    }

    @Test
    public void shouldConvertItemWithNumberToItemDTO() {
        Item item = new Item();
        item.setNumber("213767842084");
        ItemDTO itemDTO = itemConverter.toDTO(item);
        Assert.assertEquals(item.getNumber(), itemDTO.getNumber());
    }

    @Test
    public void shouldConvertItemWithDescriptionToItemDTO() {
        Item item = new Item();
        item.setDescription("Description");
        ItemDTO itemDTO = itemConverter.toDTO(item);
        Assert.assertEquals(item.getDescription(), itemDTO.getDescription());
    }

    @Test
    public void shouldConvertItemWithPriceToItemDTO() {
        Item item = new Item();
        item.setPrice(35.48);
        ItemDTO itemDTO = itemConverter.toDTO(item);
        Assert.assertEquals(item.getPrice(), itemDTO.getPrice());
    }
}
