package com.gmail.marozalena.onlinemarket.repository.pagination.impl;

import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.impl.GenericRepositoryImpl;
import com.gmail.marozalena.onlinemarket.repository.pagination.PaginationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PaginationRepositoryImpl extends GenericRepositoryImpl implements PaginationRepository {

    private static final Logger logger = LoggerFactory.getLogger(PaginationRepositoryImpl.class);

    @Override
    public int getCountOfOffset(Integer page) {
        return (page - 1) * 10;
    }

    @Override
    public int getCountOfUsers(Connection connection) {
        String sql = "SELECT COUNT(*) FROM `users` WHERE deleted = '0'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Problems with getting user from database", e);
        }
        return 0;
    }

    @Override
    public int getCountOfReviews(Connection connection) {
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
