package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.repository.model.Item;
import com.gmail.marozalena.onlinemarket.repository.model.Order;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.converter.OrderConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ItemConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.OrderConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ProfileConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.RoleConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.UserConverterImpl;
import com.gmail.marozalena.onlinemarket.service.enums.OrderEnums;
import com.gmail.marozalena.onlinemarket.service.model.ItemDTO;
import com.gmail.marozalena.onlinemarket.service.model.OrderDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderConverterTest {

    private OrderConverter orderConverter;

    @Before
    public void init(){
        orderConverter = new OrderConverterImpl(
                new ItemConverterImpl(), new UserConverterImpl(
                        new RoleConverterImpl(), new ProfileConverterImpl()));
    }

    @Test
    public void shouldConvertOrderDTOToOrder(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setNumber("826578grlmlk878");
        orderDTO.setStatus(OrderEnums.DELIVERED);
        orderDTO.setItem(new ItemDTO());
        orderDTO.setCount(2);
        orderDTO.setUser(new UserDTO());
        Order order = orderConverter.fromDTO(orderDTO);
        Assert.assertEquals(orderDTO.getId(), order.getId());
        Assert.assertEquals(orderDTO.getNumber(), order.getNumber());
        Assert.assertEquals(orderDTO.getStatus().name(), order.getStatus());
        Assert.assertEquals(orderDTO.getCount(), order.getCount());
    }

    @Test
    public void shouldConvertOrderToOrderDTO(){
        Order order = new Order();
        order.setId(1L);
        order.setNumber("826578grlmlk878");
        order.setStatus("DELIVERED");
        order.setItem(new Item());
        order.setCount(2);
        order.setUser(new User());
        OrderDTO orderDTO = orderConverter.toDTO(order);
        Assert.assertEquals(order.getId(), orderDTO.getId());
        Assert.assertEquals(order.getNumber(), orderDTO.getNumber());
        Assert.assertEquals(order.getStatus(), orderDTO.getStatus().name());
        Assert.assertEquals(order.getCount(), orderDTO.getCount());
    }
}
