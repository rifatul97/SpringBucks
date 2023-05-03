package com.rifatul.SpringBucks.controller.product;

import com.google.api.client.json.Json;
import com.rifatul.SpringBucks.domain.dto.CategoriesDto;
import com.rifatul.SpringBucks.domain.model.Category;
import com.rifatul.SpringBucks.service.product.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDto> getSubCategoriesByParentId(@RequestParam(value = "id") int id) {
        return ResponseEntity.ok(categoryService.getCategoriesByParentId(id));
    }

    //public ResponseEntity<List<CategoriesDto>> getAllCategories()
    @GetMapping
    public ResponseEntity<List<CategoriesDto>> getAllCategories() {
        var categories = categoryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/search")
    public ResponseEntity<Category> getByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(categoryService.getByName(name.replace("-", " ")));
    }

}