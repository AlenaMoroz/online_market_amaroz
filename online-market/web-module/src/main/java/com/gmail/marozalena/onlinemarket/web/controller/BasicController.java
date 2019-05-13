package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ReviewService;
import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class BasicController {

    private final ReviewService reviewService;

    @Autowired
    public BasicController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("reviews")
    public String getReviews(){
        return "redirect:/reviews/1";
    }

    @GetMapping("/reviews/{page}")
    public String getReviewsWithPage(Model model,
                                     @PathVariable("page") Integer page) {
        int pageN;
        if(page==null){
            pageN = 1;
        }else {pageN = page;}
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
    public String deleteReview(@ModelAttribute("review") ReviewDTO reviewDTO) {
        reviewService.deleteReview(reviewDTO);
        return "redirect:/reviews/1";
    }

    @PostMapping("/reviews/save")
    public String updateReviews(@ModelAttribute("reviews") ListOfReviewsDTO list){
        reviewService.updateReviews(list);
        return "redirect:/reviews/1";
    }

}
