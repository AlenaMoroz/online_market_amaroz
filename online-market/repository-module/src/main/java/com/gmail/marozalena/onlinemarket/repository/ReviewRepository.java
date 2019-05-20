package com.gmail.marozalena.onlinemarket.repository;

import com.gmail.marozalena.onlinemarket.repository.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository extends GenericRepository<Long, Review> {

    void deleteReview(Connection connection, Long id);

    void updateReview(Connection connection, Review review);
}
