package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ReviewRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    public List<Review> getReviews(Connection connection) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM `reviews`,`users` WHERE `reviews`.`users_id`=`users`.`id`";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                Review review = new Review();
                review.setId(resultSet.getLong("id"));
                review.setUser(new User());
                review.getUser().setSurname(resultSet.getString("surname"));
                review.getUser().setName(resultSet.getString("name"));
                review.getUser().setPatronymic(resultSet.getString("patronymic"));
                review.setReview(resultSet.getString("review"));
                review.setDate(resultSet.getDate("date"));
                review.setShowed(resultSet.getBoolean("isShowed"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting reviews from database", e);
        }
        return reviews;
    }
}
