package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubCategoryDao {
    void add(String name);
    void update(int id, String name);
    void delete(int categoryId);
    Optional<CategoryDto> selectById(int categoryId);
    List<CategoryDto> selectByParentId(int parentCategoryId);
}
