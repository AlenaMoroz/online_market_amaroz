package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.service.converter.ReviewConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {

    private final UserConverter userConverter;

    @Autowired
    public ReviewConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public Review fromReviewDTO(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            return null;
        }
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setUser(userConverter.fromUserDTO(reviewDTO.getUser()));
        review.setReview(reviewDTO.getReview());
        review.setDate(reviewDTO.getDate());
        review.setShowed(reviewDTO.isShowed());
        return review;
    }

    @Override
    public ReviewDTO toReviewDTO(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setUser(userConverter.toUserDTO(review.getUser()));
        reviewDTO.setReview(review.getReview());
        reviewDTO.setDate(review.getDate());
        reviewDTO.setShowed(review.isShowed());
        return reviewDTO;
    }
}
