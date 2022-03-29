package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.CategoryDto;
import com.rifatul.SpringBucks.domain.model.ParentCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ParentCategoryDao {
    List<CategoryDto> selectAll();
    Optional<CategoryDto> selectById(int id);
    void add(String name);
    void delete(int id);
    void update(int id, String name);
    void addSubCategory(int parentid, int childCategory);
}
