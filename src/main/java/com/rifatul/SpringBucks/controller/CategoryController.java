package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.domain.dto.SubCategories;
import com.rifatul.SpringBucks.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 36000)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<SubCategories.GetByParentId> getSubCategoriesByParentId(@RequestParam(value = "id") int id) {
        return ResponseEntity.ok(categoryService.getCategoriesByParentId(id));
    }

    @GetMapping
    public ResponseEntity<CategoryDtoRequest.GetAll> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

}