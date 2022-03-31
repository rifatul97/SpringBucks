package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubCategoryDao {
    void add(String name);
    void update(int id, String name);
    void delete(int categoryId);
    Optional<Category> selectById(int categoryId);
    List<Category> selectByParentId(int parentCategoryId);
}
