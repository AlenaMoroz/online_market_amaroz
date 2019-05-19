package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;

import java.util.List;

public interface ReviewService {

    PageDTO<ReviewDTO> getReviews(Integer page);

    void deleteReview(Long id);

    void updateReviews(ListOfReviewsDTO list);
}
