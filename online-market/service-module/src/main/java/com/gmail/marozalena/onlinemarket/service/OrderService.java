package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.OrderDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;

import java.util.List;

public interface OrderService {

    PageDTO<OrderDTO> getOrders(Integer page);

    PageDTO<OrderDTO> getOrdersForUser(Integer page, Long id);

    OrderDTO getOrder(Long id);

    void updateOrder(Long id, String status);

    void createOrder(String number, Integer quantity);

    List<OrderDTO> getOrders();
}
