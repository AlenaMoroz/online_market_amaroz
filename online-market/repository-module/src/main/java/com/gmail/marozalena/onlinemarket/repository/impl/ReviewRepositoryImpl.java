package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ReviewRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    public void deleteReview(Connection connection, Long id) {
        String sql = "UPDATE reviews SET deleted='1' WHERE `id`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems removing review from the database", e);
        }
    }

    @Override
    public void updateReview(Connection connection, Review review) {
        String sql = "UPDATE reviews SET showed=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, review.isShowed());
            preparedStatement.setLong(2, review.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with updating review in database", e);
        }
    }
}
