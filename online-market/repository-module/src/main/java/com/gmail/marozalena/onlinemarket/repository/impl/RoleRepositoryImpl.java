package com.gmail.marozalena.onlinemarket.repository.impl;

import com.gmail.marozalena.onlinemarket.repository.RoleRepository;
import com.gmail.marozalena.onlinemarket.repository.exception.DatabaseException;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
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
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

}
