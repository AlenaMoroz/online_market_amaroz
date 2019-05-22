package com.gmail.marozalena.onlinemarket.service.impl;

import com.gmail.marozalena.onlinemarket.repository.ReviewRepository;
import com.gmail.marozalena.onlinemarket.repository.constant.LimitConstants;
import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.service.ReviewService;
import com.gmail.marozalena.onlinemarket.service.converter.ReviewConverter;
import com.gmail.marozalena.onlinemarket.service.model.ListOfReviewsDTO;
import com.gmail.marozalena.onlinemarket.service.model.PageDTO;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        for (ReviewDTO reviewDTO : list.getListOfReviews()) {
            Review review = reviewRepository.findByID(reviewDTO.getId());
            review.setShowed(reviewDTO.isShowed());
            reviewRepository.merge(review);
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
