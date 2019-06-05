package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ItemRepository;
import com.gmail.marozalena.onlinemarket.repository.OrderRepository;
import com.gmail.marozalena.onlinemarket.repository.UserRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Item;
import com.gmail.marozalena.onlinemarket.repository.model.Order;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.OrderService;
import com.gmail.marozalena.onlinemarket.service.converter.OrderConverter;
import com.gmail.marozalena.onlinemarket.service.enums.OrderEnums;
import com.gmail.marozalena.onlinemarket.service.model.OrderDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderConverter orderConverter,
                            UserRepository userRepository,
                            ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public PageDTO<OrderDTO> getOrders(Integer page) {
        List<Order> orders = orderRepository.findAll(page);
        List<OrderDTO> list = orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
        PageDTO<OrderDTO> pageDTO = new PageDTO<>();
        pageDTO.setList(list);
        pageDTO.setCountOfPages(
                getCountOfPages(orderRepository.getCountOfEntities()));
        return pageDTO;
    }

    @Override
    @Transactional
    public PageDTO<OrderDTO> getOrdersForUser(Integer page, Long id) {
        List<Order> orders = orderRepository.findOrdersForUser(page, id);
        List<OrderDTO> list = orders.stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
        PageDTO<OrderDTO> pageDTO = new PageDTO<>();
        pageDTO.setList(list);
        pageDTO.setCountOfPages(
                getCountOfPages(orderRepository.getCountOfEntities()));
        return pageDTO;
    }

    @Override
    @Transactional
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findByID(id);
        return orderConverter.toDTO(order);
    }

    @Override
    @Transactional
    public void updateOrder(Long id, String status) {
        Order order = orderRepository.findByID(id);
        order.setStatus(status);
        orderRepository.merge(order);
    }

    @Override
    @Transactional
    public void createOrder(String number, Integer quantity) {
        Item item = itemRepository.findByNumber(number);
        Order order = new Order();
        order.setNumber(generateRandomNumber());
        order.setStatus(OrderEnums.NEW.name());
        order.setItem(item);
        order.setCount(quantity);
        order.setDate(new Date());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findUserByEmail(email);
        order.setUser(user);
        orderRepository.persist(order);
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .sorted(Comparator.comparing(Order::getDate).reversed())
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    private int getCountOfPages(int countOfItems) {
        int countOfPages = countOfItems / LimitConstants.ENTITY_ON_PAGE;
        if (countOfItems > (countOfPages * LimitConstants.ENTITY_ON_PAGE)) {
            countOfPages += 1;
        }
        return countOfPages;
    }

    private String generateRandomNumber() {
        UUID number = UUID.randomUUID();
        return number.toString();
    }
}
