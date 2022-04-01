package com.rifatul.SpringBucks.dao.mapper;

import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
@Slf4j
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Order((long) resultSet.getInt("id"),
                resultSet.getTimestamp("date_added"),
                resultSet.getTimestamp("last_updated"),
                         OrderStatus.valueOf(resultSet.getString("status")));
    }
}