package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.ReviewRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.repository.pagination.PaginationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants.REVIEWS_ON_PAGE;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    private final PaginationRepository paginationRepository;

    @Autowired
    public ReviewRepositoryImpl(PaginationRepository paginationRepository) {
        this.paginationRepository = paginationRepository;
    }

    @Override
    public List<Review> getReviews(Connection connection, Integer page) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM `reviews`,`users` WHERE `reviews`.`users_id`=`users`.`id` " +
                "AND `reviews`.`deleted`='0' LIMIT ? OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, REVIEWS_ON_PAGE);
            preparedStatement.setInt(2, paginationRepository.getCountOfOffset(page));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Review review = new Review();
                    review.setId(resultSet.getLong("id"));
                    review.setUser(new User());
                    review.getUser().setId(resultSet.getLong("users_id"));
                    review.getUser().setSurname(resultSet.getString("surname"));
                    review.getUser().setName(resultSet.getString("name"));
                    review.getUser().setPatronymic(resultSet.getString("patronymic"));
                    review.getUser().setRole(new Role());
                    review.getUser().getRole().setId(resultSet.getLong("roles_id"));
                    review.setReview(resultSet.getString("review"));
                    review.setDate(resultSet.getDate("date"));
                    review.setShowed(resultSet.getBoolean("showed"));
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting reviews from database", e);
        }
        return reviews;
    }

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
            preparedStatement.setBoolean(1, review.showed());
            preparedStatement.setLong(2, review.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with updating review in database", e);
        }
    }
}
