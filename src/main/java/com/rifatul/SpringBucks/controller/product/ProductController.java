package com.rifatul.SpringBucks.controller.product;

import com.rifatul.SpringBucks.domain.dto.CategoriesDto;
import com.rifatul.SpringBucks.domain.dto.ProductDTO;
import com.rifatul.SpringBucks.domain.model.Category;
import com.rifatul.SpringBucks.domain.model.Product;
import com.rifatul.SpringBucks.service.product.CategoryService;
import com.rifatul.SpringBucks.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam Map<String, String> params) {

        if (params.isEmpty()) {
            return ResponseEntity.ok(productService.getByCategories(categoryService.getAll()));
        }

        for (String str_id : params.values()) {
            int id = Integer.parseInt(str_id);
            for (String param_type : params.keySet()) {
                switch (param_type) {
                    case "category": {
                        List<Product> products = productService.productByCategory(id);
                        return ResponseEntity.ok(products);
                    }
                    case "parent_category": {
                        List<Product> products = productService.productByParentCategory(id);
                        return ResponseEntity.ok(products);
                    }
                    case "id": {
                        return ResponseEntity.ok(List.of(productService.getById(id).get()));
                    }
                }
            }
        }

        return ResponseEntity.ok(null);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductsByStartingName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProductData(@RequestBody ProductDTO.Request.Update request) throws Exception {
        productService.update(request);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @PostMapping("/add")
    public void addProductData(@RequestBody ProductDTO.Request.Create request) {
        log.info("{}", request.toString());
        productService.save(request);
    }

    @DeleteMapping
    public void deleteProductData(@RequestBody ProductDTO.Request.Delete request) throws InterruptedException {
        productService.delete(request);
    }


}