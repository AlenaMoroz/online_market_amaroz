package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ReviewRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.service.ReviewService;
import com.gmail.marozalena.onlinemarket.service.converter.ReviewConverter;
import com.gmail.marozalena.onlinemarket.service.exception.ReviewsNotUpdatedException;
import com.gmail.marozalena.onlinemarket.service.exception.ServiceException;
import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public PageDTO<ReviewDTO> getReviews(Integer page) {
        List<Review> reviews = reviewRepository.findAll(page);
        List<ReviewDTO> list = reviews.stream()
                .map(reviewConverter::toReviewDTO)
                .collect(Collectors.toList());
        PageDTO<ReviewDTO> pageDTO = new PageDTO<>();
        pageDTO.setList(list);

        pageDTO.setCountOfPages(
                getCountOfPages(reviewRepository.getCountOfEntities()));
        return pageDTO;
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findByID(id);
        reviewRepository.remove(review);
    }

    @Override
    @Transactional
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

    private int getCountOfPages(int countOfReviews) {
        int countOfPages = countOfReviews / LimitConstants.ENTITY_ON_PAGE;
        if (countOfReviews > (countOfPages * LimitConstants.ENTITY_ON_PAGE)) {
            countOfPages += 1;
        }
        return countOfPages;
    }
}
