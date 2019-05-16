package com.gmail.marozalena.onlinemarket.repository.pagination;

import com.gmail.marozalena.onlinemarket.repository.GenericRepository;

import java.sql.Connection;

public interface PaginationRepository extends GenericRepository {

    int getCountOfOffset(Integer page);

    int getCountOfUsers(Connection connection);

    int getCountOfReviews(Connection connection);
}
