package com.rifatul.SpringBucks.dao.mapper;

import com.rifatul.SpringBucks.domain.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDtoRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Category(resultSet.getInt("id"), resultSet.getString("name"));
    }
}