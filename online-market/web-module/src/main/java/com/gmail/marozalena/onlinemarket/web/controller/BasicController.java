package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ReviewService;
import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
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

    private final ReviewService reviewService;
    private static final String redirectToReviewFirstPage = "redirect:/reviews/1";

    @Autowired
    public BasicController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("reviews")
    public String getReviews() {
        return redirectToReviewFirstPage;
    }

    @GetMapping("/reviews/{page}")
    public String getReviewsWithPage(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page) {
        int pageN;
        if (page == null) {
            pageN = 1;
        } else {
            pageN = page;
        }
        int countPages = reviewService.getCountPages();
        if (pageN > countPages && countPages > 0) {
            pageN = countPages;
        }
        List<Integer> countOfPages = IntStream.rangeClosed(1, countPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", countOfPages);
        model.addAttribute("current", pageN);
        List<ReviewDTO> reviews = reviewService.getReviews(pageN);
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
