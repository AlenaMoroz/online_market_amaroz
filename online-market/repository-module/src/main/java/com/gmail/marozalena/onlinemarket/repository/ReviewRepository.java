package com.gmail.marozalena.onlinemarket.repository;

import com.gmail.marozalena.onlinemarket.repository.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository extends GenericRepository {

    List<Review> getReviews(Connection connection, Integer page);

    void deleteReview(Connection connection, Review review);

    void updateReviews(Connection connection, List<Review> reviews);

    int countOfReviews(Connection connection);
}
