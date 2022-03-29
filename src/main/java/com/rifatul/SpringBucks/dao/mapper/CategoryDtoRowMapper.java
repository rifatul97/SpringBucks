package com.rifatul.SpringBucks.dao.mapper;

import com.rifatul.SpringBucks.domain.model.CategoryDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDtoRowMapper implements RowMapper<CategoryDto> {
    @Override
    public CategoryDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new CategoryDto(resultSet.getInt("id"), resultSet.getString("name"));
    }
}