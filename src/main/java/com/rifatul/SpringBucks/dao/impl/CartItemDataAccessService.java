package com.rifatul.SpringBucks.dao.impl;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.mapper.CartItemRowMapper;
import com.rifatul.SpringBucks.domain.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.utils.GetSQLQuery.getSQLQuery;

@Repository
@AllArgsConstructor
@Slf4j
public class CartItemDataAccessService implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addCartItem(long orderId, int productId, int quantity) {
        var sql = getSQLQuery("add_cartItem");
        jdbcTemplate.update(sql, orderId, productId, quantity);
    }

    @Override
    public void updateCartItem(int cartItemId, int productId, int quantity) {
        var sql = getSQLQuery("update_cartItem");
        jdbcTemplate.update(sql, productId, quantity, cartItemId);
    }

    @Override
    public void deleteCartItem(int cartItemId) {
        var sql = getSQLQuery("delete_cartItem");
        jdbcTemplate.update(sql, cartItemId);
    }

    @Override
    public Optional<CartItem> selectById(int cartId) {
        var sql = getSQLQuery("select_cartItem_by_id");
        return jdbcTemplate.query(sql, new CartItemRowMapper(), cartId).stream()
                .findFirst();
    }

    @Override
    public List<CartItem> selectByOrderId(long orderId) {
        var sql = getSQLQuery("select_user_cartItems");
        return jdbcTemplate.query(sql, new CartItemRowMapper(), orderId);
    }

}