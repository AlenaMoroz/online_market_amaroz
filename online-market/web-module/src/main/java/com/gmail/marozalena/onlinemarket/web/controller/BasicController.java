package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ReviewService;
import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import com.gmail.marozalena.onlinemarket.service.pagination.PaginationService;
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
    private final PaginationService paginationService;

    @Autowired
    public BasicController(ReviewService reviewService,
                           PaginationService paginationService) {
        this.reviewService = reviewService;
        this.paginationService = paginationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/reviews")
    public String getReviewsWithPage(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page) {

        int countPages = paginationService.getCountPagesForPageWithReviews();
        if (page > countPages && countPages > 0) {
            page = countPages;
        }
        List<Integer> countOfPages = IntStream.rangeClosed(1, countPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", countOfPages);
        model.addAttribute("current", page);
        List<ReviewDTO> reviews = reviewService.getReviews(page);
        ListOfReviewsDTO list = new ListOfReviewsDTO();
        list.setListOfReviews(reviews);
        model.addAttribute("reviews", list);
        return "reviews";
    }

    @PostMapping("/reviews/delete")
    public String deleteReview(@RequestParam("delete") Long id) {
        reviewService.deleteReview(id);
        return redirectToReviewFirstPage;
    }

    @PostMapping("/reviews/save")
    public String updateReviews(@ModelAttribute("reviews") ListOfReviewsDTO list) {
        reviewService.updateReviews(list);
        return redirectToReviewFirstPage;
    }

}
