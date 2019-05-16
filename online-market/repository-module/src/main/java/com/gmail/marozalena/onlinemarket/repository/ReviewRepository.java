package com.gmail.marozalena.onlinemarket.repository;

import com.gmail.marozalena.onlinemarket.repository.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository extends GenericRepository {

    List<Review> getReviews(Connection connection, Integer page);

    void deleteReview(Connection connection, Long id);

    void updateReviews(Connection connection, Review review);

    int getCountOfReviews(Connection connection);
}
