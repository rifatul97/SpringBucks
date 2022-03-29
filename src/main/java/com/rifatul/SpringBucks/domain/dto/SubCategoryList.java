package com.rifatul.SpringBucks.domain.dto;

import com.rifatul.SpringBucks.domain.model.CategoryDto;

import java.util.List;

public record SubCategoryList(CategoryDto parent, List<CategoryDto> childrens) {

}