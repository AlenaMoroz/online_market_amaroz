package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ReviewRepository;
import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.service.ReviewService;
import com.gmail.marozalena.onlinemarket.service.converter.ReviewConverter;
import com.gmail.marozalena.onlinemarket.service.exception.ReviewNotDeletedException;
import com.gmail.marozalena.onlinemarket.service.exception.ReviewNotFoundException;
import com.gmail.marozalena.onlinemarket.service.exception.ReviewsNotUpdatedException;
import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    public List<ReviewDTO> getReviews(Integer page) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Review> reviews = reviewRepository.getReviews(connection, page);
                List<ReviewDTO> list = reviews.stream()
                        .map(reviewConverter::toReviewDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return list;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ReviewNotFoundException("Reviews not found in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ReviewNotFoundException("Reviews not found in database", e);
        }
    }

    @Override
    public void deleteReview(Long id) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                reviewRepository.deleteReview(connection, id);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ReviewNotDeletedException("Review not deleted from database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ReviewNotDeletedException("Review not deleted from database", e);
        }
    }

    @Override
    public void updateReviews(ListOfReviewsDTO list) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Review> reviews = list.getListOfReviews()
                        .stream()
                        .map(reviewConverter::fromReviewDTO)
                        .collect(Collectors.toList());
                for (Review review : reviews) {
                    reviewRepository.updateReview(connection, review);
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ReviewsNotUpdatedException("Reviews not updated in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ReviewsNotUpdatedException("Reviews not updated in database", e);
        }
    }

    @Override
    public int getCountPages() {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int usersNumber = reviewRepository.getCountOfReviews(connection);
                int pagesNumber = usersNumber / 10;
                if (usersNumber > (pagesNumber * 10)) {
                    pagesNumber += 1;
                }
                connection.commit();
                return pagesNumber;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ReviewNotFoundException("Reviews not found in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ReviewNotFoundException("Reviews not found in database", e);
        }
    }
}
