package com.rifatul.SpringBucks.dao.impl;

import com.rifatul.SpringBucks.dao.ParentCategoryDao;
import com.rifatul.SpringBucks.dao.mapper.CategoryDtoRowMapper;
import com.rifatul.SpringBucks.domain.model.CategoryDto;
import com.rifatul.SpringBucks.domain.model.ParentCategory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.utils.GetSQLQuery.getSQLQuery;

@Repository @AllArgsConstructor @Slf4j
public class ParentCategoryDataAccessService implements ParentCategoryDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CategoryDto> selectAll() {
        var sql = String.format(getSQLQuery("select_categories"), "parent_category");
        return jdbcTemplate.query(sql, new CategoryDtoRowMapper());
    }

    @Override
    public Optional<CategoryDto> selectById(int id) {
        var sql = String.format(getSQLQuery("select_category_by_id"), "parent_category");
        return jdbcTemplate.query(sql, new CategoryDtoRowMapper(), id).stream().findFirst();
    }

    @Override
    public void add(String name) {
        var sql =  String.format(getSQLQuery("insert_category"), "parent_category");
        jdbcTemplate.update(sql, name);
    }

    @Override
    public void delete(int id) {
        var sql = String.format(getSQLQuery("delete_category"), "parent_category");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void addSubCategory(int parentId, int subId) {
        var sql = getSQLQuery("link_sub_category_to_a_parent_category");
        jdbcTemplate.update(sql, parentId, subId);
    }

    public void removeSubCategory(int parentId, int subId) {
        var sql = getSQLQuery("remove_sub_category_link");
        jdbcTemplate.update(sql, parentId, subId);
    }

    @Override
    public void update(int id, String name) {
        var sql = String.format(getSQLQuery("update_category"), "parent_category");
        jdbcTemplate.update(sql, name, id);
    }

}
