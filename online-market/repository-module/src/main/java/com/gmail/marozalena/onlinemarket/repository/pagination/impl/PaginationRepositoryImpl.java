package com.gmail.marozalena.onlinemarket.repository.pagination.impl;

import com.gmail.marozalena.onlinemarket.repository.pagination.PaginationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaginationRepositoryImpl implements PaginationRepository {

    @Override
    public int getCountOfOffset(Integer page) {
        return (page-1)*10;
    }
}
