package com.rifatul.SpringBucks.dao.impl;

import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.mapper.ProductRowMapper;
import com.rifatul.SpringBucks.domain.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.utils.GetSQLQuery.getSQLQuery;

@Repository
@AllArgsConstructor
public class ProductDataAccessService implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> selectByCategoryId(int category_id) {
        var sql = getSQLQuery("select_products_by_categoryId");
        return jdbcTemplate.query(sql, new ProductRowMapper(), category_id);
    }

    @Override
    public Optional<List<Product>> selectByName(String name) {
        var sql = getSQLQuery("select_products_by_name");
        return Optional.of(jdbcTemplate.query(sql, new ProductRowMapper(), name));
    }

    @Override
    public Optional<Product> selectById(int productId) {
        var sql = getSQLQuery("select_product_by_id");
        return jdbcTemplate.query(sql, new ProductRowMapper(), productId).stream().findFirst();
    }

    @Override
    public void updateProduct(int productId, int categoryId, double price) {
        var sql = getSQLQuery("update_product_by_id");
        jdbcTemplate.update(sql, price, categoryId, productId);
    }

    @Override
    public void deleteProduct(int productId) {

    }

    @Override
    public void save(String name, double price, int categoryId) {

    }

}