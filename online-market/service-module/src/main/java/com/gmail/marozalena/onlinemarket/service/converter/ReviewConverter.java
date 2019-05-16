package com.gmail.marozalena.onlinemarket.service.converter;

import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;

public interface ReviewConverter {

    Review fromReviewDTO(ReviewDTO reviewDTO);

    ReviewDTO toReviewDTO(Review review);
}
