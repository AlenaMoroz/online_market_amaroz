package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.OrderService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.enums.OrderEnums;
import com.gmail.marozalena.onlinemarket.service.model.OrderDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_CREATE_ORDER;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ORDERS_PAGE_FOR_CUSTOMER_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ORDERS_PAGE_FOR_SALE_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ORDER_PAGE;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_UPDATE_ORDER_PAGE;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService,
                           UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping(URL_TO_ORDERS_PAGE_FOR_SALE_USER)
    public String getOrdersForSaleUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       Model model) {
        PageDTO<OrderDTO> orders = orderService.getOrders(page);
        model.addAttribute("orders", orders.getList());
        int countOfPages = orders.getCountOfPages();
        if (page > countOfPages && countOfPages > 0) {
            page = countOfPages;
        }
        model.addAttribute("current", page);
        List<Integer> pages = IntStream.rangeClosed(1, countOfPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", pages);
        return "ordersForSaleUser";
    }

    @GetMapping(URL_TO_ORDERS_PAGE_FOR_CUSTOMER_USER)
    public String getOrdersForCustomerUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserDTO userDTO = userService.loadUserByEmail(email);
        PageDTO<OrderDTO> orders = orderService.getOrdersForUser(page, userDTO.getId());
        model.addAttribute("orders", orders.getList());
        int countOfPages = orders.getCountOfPages();
        if (page > countOfPages && countOfPages > 0) {
            page = countOfPages;
        }
        model.addAttribute("current", page);
        List<Integer> pages = IntStream.rangeClosed(1, countOfPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", pages);
        return "orders";
    }

    @GetMapping(URL_TO_ORDER_PAGE)
    public String getOrder(@PathVariable Long id,
                           Model model){
        OrderDTO orderDTO = orderService.getOrder(id);
        model.addAttribute("order", orderDTO);
        OrderEnums[] statuses = OrderEnums.values();
        model.addAttribute("statuses", statuses);
        return "order";
    }

    @PostMapping(URL_TO_UPDATE_ORDER_PAGE)
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam String status,
                                    Model model){
        orderService.updateOrder(id, status);
        OrderDTO orderDTO = orderService.getOrder(id);
        model.addAttribute("order", orderDTO);
        OrderEnums[] statuses = OrderEnums.values();
        model.addAttribute("statuses", statuses);
        return "order";
    }

    @PostMapping(URL_TO_CREATE_ORDER)
    public String createOrder(@RequestParam String number,
                              @RequestParam Integer quantity){
        orderService.createOrder(number, quantity);
        return "redirect:/orders";
    }
}
