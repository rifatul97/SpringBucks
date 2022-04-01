package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.ParentCategoryDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.domain.dto.CategoriesDto;
import com.rifatul.SpringBucks.domain.model.Category;
import com.rifatul.SpringBucks.exception.CategoryDoesNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class CategoryService {

    @Autowired private final ParentCategoryDao parentCategoryDao;
    @Autowired private final SubCategoryDao subCategoryDao;

    public List<Category> getAllParentCategories() {
        return parentCategoryDao.selectAll();
    }

    public Category getParentCategoryById(int id) {
        return parentCategoryDao.selectById(id)
                .orElseThrow(() -> new CategoryDoesNotExistException(id));
    }

    public List<Category> getSubCategoriesByParentId(int parentId) {
        return subCategoryDao.selectByParentId(parentId);
    }

    public List<CategoriesDto> getAll() {

        return getAllParentCategories()
                .stream()
                .map(parentCategoryDto -> new CategoriesDto(parentCategoryDto, getSubCategoriesByParentId(parentCategoryDto.id())))
                .collect(Collectors.toList());
    }

    public CategoriesDto getCategoriesByParentId(int id) {
        return new CategoriesDto(getParentCategoryById(id), getSubCategoriesByParentId(id));
    }
}
