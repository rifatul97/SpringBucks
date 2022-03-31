package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ParentCategoryDao {
    List<Category> selectAll();
    Optional<Category> selectById(int id);
    void add(String name);
    void delete(int id);
    void update(int id, String name);
    void addSubCategory(int parentid, int childCategory);
}
