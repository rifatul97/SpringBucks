package com.rifatul.SpringBucks.dao.mapper;

import com.rifatul.SpringBucks.domain.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Role(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"));
    }
}