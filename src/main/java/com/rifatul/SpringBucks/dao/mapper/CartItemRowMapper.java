package com.rifatul.SpringBucks.dao.mapper;

import com.rifatul.SpringBucks.domain.model.CartItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemRowMapper implements RowMapper<CartItem> {

    @Override
    public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CartItem(rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getInt("product_id"),
                rs.getInt("quantity"),
                rs.getTimestamp("last_updated"));
    }

}