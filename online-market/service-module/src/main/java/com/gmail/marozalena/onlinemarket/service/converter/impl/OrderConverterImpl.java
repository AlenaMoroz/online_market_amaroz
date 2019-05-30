package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Order;
import com.gmail.marozalena.onlinemarket.service.converter.ItemConverter;
import com.gmail.marozalena.onlinemarket.service.converter.OrderConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.enums.OrderEnums;
import com.gmail.marozalena.onlinemarket.service.model.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverterImpl implements OrderConverter {

    private final ItemConverter itemConverter;
    private final UserConverter userConverter;

    @Autowired
    public OrderConverterImpl(ItemConverter itemConverter,
                              UserConverter userConverter) {
        this.itemConverter = itemConverter;
        this.userConverter = userConverter;
    }


    @Override
    public Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setNumber(orderDTO.getNumber());
        order.setStatus(orderDTO.getStatus().name());
        order.setItem(itemConverter.fromDTO(orderDTO.getItem()));
        order.setCount(orderDTO.getCount());
        order.setUser(userConverter.fromUserDTO(orderDTO.getUser()));
        return order;
    }

    @Override
    public OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setNumber(order.getNumber());
        orderDTO.setStatus(OrderEnums.valueOf(order.getStatus()));
        orderDTO.setItem(itemConverter.toDTO(order.getItem()));
        orderDTO.setCount(order.getCount());
        orderDTO.setUser(userConverter.toUserDTO(order.getUser()));
        return orderDTO;
    }
}
