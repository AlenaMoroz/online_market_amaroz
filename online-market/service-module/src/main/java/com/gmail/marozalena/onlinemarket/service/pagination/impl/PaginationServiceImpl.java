package com.gmail.marozalena.onlinemarket.service.pagination.impl;

import com.gmail.marozalena.onlinemarket.repository.pagination.PaginationRepository;
import com.gmail.marozalena.onlinemarket.service.exception.ReviewNotFoundException;
import com.gmail.marozalena.onlinemarket.service.exception.UserNotFoundException;
import com.gmail.marozalena.onlinemarket.service.pagination.PaginationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;

public class PaginationServiceImpl implements PaginationService {

    private static final Logger logger = LoggerFactory.getLogger(PaginationServiceImpl.class);

    private final PaginationRepository paginationRepository;

    @Autowired
    public PaginationServiceImpl(PaginationRepository paginationRepository) {
        this.paginationRepository = paginationRepository;
    }

    @Override
    public int getCountPagesForPageWithUsers() {
        try (Connection connection = paginationRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int usersNumber = paginationRepository.getCountOfUsers(connection);
                int pagesNumber = usersNumber / 10;
                if (usersNumber > (pagesNumber * 10)) {
                    pagesNumber += 1;
                }
                connection.commit();
                return pagesNumber;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserNotFoundException("Users not found in database", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserNotFoundException("Users not found in database", e);
        }
    }

    @Override
    public int getCountPagesForPageWithReviews() {
        try (Connection connection = paginationRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int usersNumber = paginationRepository.getCountOfReviews(connection);
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
