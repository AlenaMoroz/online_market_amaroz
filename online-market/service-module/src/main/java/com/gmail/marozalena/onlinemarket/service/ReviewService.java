package com.gmail.marozalena.onlinemarket.service;

import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;

public interface ReviewService {

    PageDTO<ReviewDTO> getReviews(Integer page);

    void deleteReview(Long id);

    void updateReviews(ListOfReviewsDTO list);

    void createReview(ReviewDTO reviewDTO, UserDTO user);
}
