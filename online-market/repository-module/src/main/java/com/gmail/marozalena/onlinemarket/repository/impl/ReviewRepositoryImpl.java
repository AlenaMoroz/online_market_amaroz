package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ReviewRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    public List<Review> getReviews(Connection connection, Integer page) {
        List<Review> reviews = new ArrayList<>();
        String sql = String.format("SELECT * FROM `reviews`,`users` WHERE `reviews`.`users_id`=`users`.`id` AND `reviews`.`deleted`='0' " +
                "LIMIT 10 OFFSET %d", (page-1)*10);
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                Review review = new Review();
                review.setId(resultSet.getLong("id"));
                review.setUser(new User());
                review.getUser().setId(resultSet.getLong("users_id"));
                review.getUser().setSurname(resultSet.getString("surname"));
                review.getUser().setName(resultSet.getString("name"));
                review.getUser().setPatronymic(resultSet.getString("patronymic"));
                review.setReview(resultSet.getString("review"));
                review.setDate(resultSet.getDate("date"));
                review.setShowed(resultSet.getBoolean("showed"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting reviews from database", e);
        }
        return reviews;
    }

    @Override
    public void deleteReview(Connection connection, Review review) {
        String sql = String.format("UPDATE `reviews` SET `deleted`='1' WHERE `id`='%d'", review.getId());
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems removing review from the database", e);
        }
    }

    @Override
    public void updateReviews(Connection connection, List<Review> reviews) {
        for (Review review : reviews) {
            String sql = "UPDATE `reviews` SET `showed`= ? WHERE `id`=?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setBoolean(1, review.showed());
                preparedStatement.setLong(2, review.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new DatabaseException("Problems with updating review in database", e);
            }
        }
    }

    @Override
    public int countOfReviews(Connection connection) {
        String sql = "SELECT COUNT(*) FROM `reviews` WHERE deleted = '0'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting review from database", e);
        }
        return 0;
    }

}
