package com.gmail.marozalena.onlinemarket.service.converter.impl;

import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.service.converter.ReviewConverter;
import com.gmail.marozalena.onlinemarket.service.converter.UserConverter;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.gmail.marozalena.onlinemarket.service.constant.DateConstants.PATTERN_FOR_DATE;

@Component
public class ReviewConverterImpl implements ReviewConverter {

    private static final Logger logger = LoggerFactory.getLogger(ReviewConverterImpl.class);
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
        if (reviewDTO.getDate() != null) {
            try {
                review.setDate(new SimpleDateFormat(PATTERN_FOR_DATE).parse(reviewDTO.getDate()));
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
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
        if (review.getDate() != null) {
            reviewDTO.setDate(new SimpleDateFormat(PATTERN_FOR_DATE).format(review.getDate()));
        }
        reviewDTO.setShowed(review.isShowed());
        return reviewDTO;
    }
}
