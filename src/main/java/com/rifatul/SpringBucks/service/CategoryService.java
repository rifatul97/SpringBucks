package com.rifatul.SpringBucks.service;
import com.rifatul.SpringBucks.dao.ParentCategoryDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.domain.dto.SubCategoryList;
import com.rifatul.SpringBucks.domain.model.CategoryDto;
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

    public List<CategoryDto> getAllParentCategories() {
        List<CategoryDto> parentCategories = parentCategoryDao.selectAll();
        return parentCategories;
    }

    public CategoryDto getParentCategoryById(int id) {
        return parentCategoryDao.selectById(id).get();
    }

    public List<CategoryDto> getSubCategoriesByParentId(int parentId) {
        List<CategoryDto> subCategoriesByParentId = subCategoryDao.selectByParentId(parentId);
        return subCategoriesByParentId;
    }

    public List<SubCategoryList> getAll() {

        return getAllParentCategories()
                .stream()
                .map(parentCategoryDto -> new SubCategoryList(parentCategoryDto, getSubCategoriesByParentId(parentCategoryDto.id())))
                .collect(Collectors.toList());
    }

    public SubCategoryList getCategoriesByParentId(int id) {
        return new SubCategoryList(getParentCategoryById(id), getSubCategoriesByParentId(id));
    }
}
