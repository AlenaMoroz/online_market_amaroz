package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ItemService;
import com.gmail.marozalena.onlinemarket.service.model.ItemDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getItems(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           Model model) {
        PageDTO<ItemDTO> items = itemService.findItems(page);
        model.addAttribute("items", items.getList());
        int countOfPages = items.getCountOfPages();
        if (page > countOfPages && countOfPages > 0) {
            page = countOfPages;
        }
        model.addAttribute("current", page);
        List<Integer> pages = IntStream.rangeClosed(1, countOfPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", pages);
        return "items";
    }

    @PostMapping("/items/{id}/copy")
    public String copyItem(@PathVariable Long id){
        itemService.copyItem(id);
        return "redirect:/items";
    }

    @PostMapping("/items/{id}/delete")
    public String deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
        return "redirect:/items";
    }

    @GetMapping("/items/{id}")
    public String getItem(@PathVariable Long id,
                          Model model){
        ItemDTO item = itemService.findByID(id);
        model.addAttribute("item", item);
        return "item";
    }
}
