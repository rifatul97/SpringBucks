package com.rifatul.SpringBucks.domain.dto;

import com.rifatul.SpringBucks.domain.model.Category;

import java.util.List;

public record CategoriesDto (Category parent, List<Category> child) {
}
