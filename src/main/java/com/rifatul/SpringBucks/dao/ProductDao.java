package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductDao {
    List<Product> selectByCategoryId(int categoryId);
    Optional<List<Product>> selectByName(String name);
    Optional<Product> selectById(int productId);
    void updateProduct(int productId, int categoryId, double price);
    void deleteProduct(int productId);
    void save(String name, double price, int categoryId);
}