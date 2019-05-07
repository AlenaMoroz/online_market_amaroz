package com.gmail.marozalena.onlinemarket.converter;

import com.gmail.marozalena.onlinemarket.model.Review;
import com.gmail.marozalena.onlinemarket.model.ReviewDTO;

public interface ReviewConverter {

    Review fromReviewDTO(ReviewDTO reviewDTO);

    ReviewDTO toReviewDTO(Review review);
}
