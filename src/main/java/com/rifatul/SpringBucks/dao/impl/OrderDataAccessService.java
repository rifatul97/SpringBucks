package com.rifatul.SpringBucks.dao.impl;

import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.mapper.OrderRowMapper;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
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
public class OrderDataAccessService implements OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Long createNewId() {
        var sql = getSQLQuery("generate_new_orderId");
        Long newOrderId = jdbcTemplate.queryForObject(sql, Long.class);
        return newOrderId;
    }

    @Override
    public Optional<Order> selectById(long id) {
        var sql = getSQLQuery("select_order_by_Id");
        return jdbcTemplate.query(sql, new OrderRowMapper(), id).stream().findFirst();
    }

    @Override
    public void mapIdToUser(int userId, long orderId) {
        var sql = getSQLQuery("map_user_order");
        jdbcTemplate.update(sql, userId, orderId);
    }

    @Override
    public List<Order> selectByStatus(OrderStatus status) {
        var sql = getSQLQuery("select_orderIds_by_orderStatus");
        return jdbcTemplate.query(sql, new OrderRowMapper(), status.toString());
    }

    @Override
    public Optional<Order> selectByUserId(int userId) {
        var sql = getSQLQuery("select_orderId_by_userId");
        return jdbcTemplate.query(sql, new OrderRowMapper(), userId).stream().findFirst();
    }

    @Override
    public void updateStatus(int orderId, OrderStatus newStatus) {
        var sql = getSQLQuery("update_order_status");
        jdbcTemplate.update(sql, orderId, newStatus.toString());
    }

}