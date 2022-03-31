package com.rifatul.SpringBucks.service;
import com.rifatul.SpringBucks.dao.ParentCategoryDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.domain.dto.CategoryDTO;
import com.rifatul.SpringBucks.domain.dto.SubCategories;
import com.rifatul.SpringBucks.domain.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class CategoryService {

    @Autowired
    private final ParentCategoryDao parentCategoryDao;
    @Autowired
    private final SubCategoryDao subCategoryDao;

    public List<Category> getAllParentCategories() {
        List<Category> parentCategories = parentCategoryDao.selectAll();
        return parentCategories;
    }

    public Category getParentCategoryById(int id) {
        return parentCategoryDao.selectById(id).get();
    }

    public List<Category> getSubCategoriesByParentId(int parentId) {
        List<Category> subCategoriesByParentId = subCategoryDao.selectByParentId(parentId);
        return subCategoriesByParentId;
    }

    public CategoryDTO.Request getAll() {

        return new CategoryDTO.Request.GetAll(getAllParentCategories()
                .stream()
                .map(parentCategoryDto -> new CategoryDTO.Child.GetAllByParentId(parentCategoryDto, getSubCategoriesByParentId(parentCategoryDto.id())))
                .collect(Collectors.toList()));
    }

    public SubCategories.GetByParentId getCategoriesByParentId(int id) {
        return new SubCategories.GetByParentId(getParentCategoryById(id), getSubCategoriesByParentId(id));
    }
}
