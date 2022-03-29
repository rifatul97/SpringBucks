package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.domain.dto.AddNewProductRequest;
import com.rifatul.SpringBucks.domain.dto.DeleteProductRequest;
import com.rifatul.SpringBucks.domain.dto.ProductsByCategoryRequest;
import com.rifatul.SpringBucks.domain.dto.UpdateProductRequest;
import com.rifatul.SpringBucks.domain.model.Product;
import com.rifatul.SpringBucks.service.CategoryService;
import com.rifatul.SpringBucks.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@RequestParam(value = "id") int id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductsByStartingName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam(value = "category") int category) {
        List<Product> products = productService.productByCategory(category);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/update")
    public void updateProductData(@RequestBody UpdateProductRequest request) {
        productService.update(request);
    }

    @PostMapping("/add")
    public void addProductData(@RequestBody AddNewProductRequest request) {
        productService.save(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductData(@RequestParam(value = "id") int id) {
        productService.delete(id);
    }


}