package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.domain.dto.AddNewProductRequest;
import com.rifatul.SpringBucks.domain.dto.UpdateProductRequest;
import com.rifatul.SpringBucks.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rifatul.SpringBucks.service.ProductValidatorService.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ProductService {

    @Autowired private final ProductDao productDao;
    @Autowired private final SubCategoryDao subCategoryDao;
//    @Autowired private final UserCartDao userCartDao;

    public void save(AddNewProductRequest req) {
        throwExceptionIfCategoryIdDoesNotExist(req.categoryId(), subCategoryDao);
        throwExceptionIfPriceIsIncorrectFormat(req.price());
        throwExceptionIfProductNameAlreadyExist(req.name(), productDao);

        productDao.save(req.name(), req.price(), req.categoryId());
    }

    public void update (UpdateProductRequest req) {
        throwExceptionIfProductIdDoesNotExist(req.id(), productDao);
        throwExceptionIfCategoryIdDoesNotExist(req.categoryId(), subCategoryDao);
        throwExceptionIfPriceIsIncorrectFormat(req.price());
//        throwExceptionIfProductIsInUserCart(req.id());

        productDao.updateProduct(req.id(), req.categoryId(), req.price());
    }

    public void delete (int productId) {
        throwExceptionIfProductIdDoesNotExist(productId, productDao);
//        throwExceptionIfProductIsInUserCart(req.id());

        productDao.deleteProduct(productId);
    }

    public List<Product> searchByName(String name) {
        List<Product> productsByName = productDao.selectByName(name)
                .orElseThrow(RuntimeException::new);

        return productsByName;
    }

    public List<Product> productByCategory(int categoryId) {
        throwExceptionIfCategoryIdDoesNotExist(categoryId, subCategoryDao);

        return productDao.selectByCategoryId(categoryId);
    }

}