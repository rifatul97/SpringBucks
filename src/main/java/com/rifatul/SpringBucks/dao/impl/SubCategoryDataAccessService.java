package com.rifatul.SpringBucks.dao.impl;

import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.dao.mapper.CategoryDtoRowMapper;
import com.rifatul.SpringBucks.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.utils.GetSQLQuery.getSQLQuery;
@Repository
@AllArgsConstructor
@Slf4j
public class SubCategoryDataAccessService implements SubCategoryDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(String name) {
        var sql =  String.format(getSQLQuery("insert_category"), "sub_category");
        jdbcTemplate.update(sql, name);
    }

    @Override
    public void update(int id, String name) {
        var sql = String.format(getSQLQuery("update_category"), "sub_category");
        jdbcTemplate.update(sql, name, id);
    }

    @Override
    public void delete(int id) {
        var sql = String.format(getSQLQuery("delete_category"), "sub_category");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Category> selectById(int id) {
        var sql = String.format(getSQLQuery("select_category_by_id"), "sub_category");
        return jdbcTemplate.query(sql, new CategoryDtoRowMapper(), id).stream().findFirst();
    }

    @Override
    public List<Category> selectByParentId(int parentCategoryId) {
        var sql = getSQLQuery("select_categories_by_parentId");
        return jdbcTemplate.query(sql, new CategoryDtoRowMapper(), parentCategoryId);
    }
}
