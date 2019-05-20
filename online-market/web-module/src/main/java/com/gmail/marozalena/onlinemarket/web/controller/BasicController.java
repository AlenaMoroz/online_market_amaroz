package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ReviewService;
import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class BasicController {

    private static final String redirectToReviewFirstPage = "redirect:/reviews";

    private final ReviewService reviewService;

    @Autowired
    public BasicController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/reviews")
    public String getReviewsWithPage(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageDTO<ReviewDTO> reviews = reviewService.getReviews(page);
        ListOfReviewsDTO list = new ListOfReviewsDTO();
        list.setListOfReviews(reviews.getList());
        model.addAttribute("reviews", list);
        int countOfPages = reviews.getCountOfPages();
        if (page > countOfPages && countOfPages > 0) {
            page = countOfPages;
        }
        model.addAttribute("current", page);
        List<Integer> pages = IntStream.rangeClosed(1, countOfPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", pages);
        return "reviews";
    }

    @PostMapping("/reviews/delete")
    public String deleteReview(@RequestParam("delete") Long id) {
        reviewService.deleteReview(id);
        return redirectToReviewFirstPage;
    }

    @PostMapping("/reviews/save")
    public String updateReviews(@ModelAttribute("reviews") ListOfReviewsDTO reviews) {
        reviewService.updateReviews(reviews);
        return redirectToReviewFirstPage;
    }

}
