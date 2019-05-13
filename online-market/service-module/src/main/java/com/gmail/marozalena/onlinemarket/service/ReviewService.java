package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getReviews(Integer page);

    void deleteReview(ReviewDTO reviewDTO);

    void updateReviews(ListOfReviewsDTO list);

    int getCountPages();
}
