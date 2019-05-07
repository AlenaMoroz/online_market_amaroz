package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ReviewRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Review;

import java.sql.Connection;
import java.util.List;

public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {

    @Override
    public List<Review> getReviews(Connection connection) {
        return null;
    }
}
