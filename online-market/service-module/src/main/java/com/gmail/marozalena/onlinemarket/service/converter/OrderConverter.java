package com.gmail.marozalena.onlinemarket.service.converter;

import com.gmail.marozalena.onlinemarket.repository.model.Order;
import com.gmail.marozalena.onlinemarket.service.model.OrderDTO;

public interface OrderConverter {

    Order fromDTO(OrderDTO orderDTO);

    OrderDTO toDTO(Order order);
}
