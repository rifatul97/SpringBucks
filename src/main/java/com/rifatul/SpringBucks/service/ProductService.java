package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.ProductDTO;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
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
//    @Autowired private final CartDao userCartDao;

    public void save(ProductDTO.Request.Create req) {
        throwExceptionIfCategoryIdDoesNotExist(req.getCategoryId(), subCategoryDao);
        throwExceptionIfPriceIsIncorrectFormat(req.getPrice());
        throwExceptionIfProductNameAlreadyExist(req.getName(), productDao);

        productDao.save(req.getName(), req.getPrice(), req.getCategoryId());
    }

    public void update (ProductDTO.Request.Update req) {
        throwExceptionIfProductIdDoesNotExist(req.getId(), productDao);
        throwExceptionIfCategoryIdDoesNotExist(req.getCategoryId(), subCategoryDao);
        throwExceptionIfPriceIsIncorrectFormat(req.getPrice());
//        throwExceptionIfProductIsInUserCart(req.id());

        productDao.updateProduct(req.getId(), req.getCategoryId(), req.getPrice());
    }

    public void delete (ProductDTO.Request.Delete request) {
        throwExceptionIfProductIdDoesNotExist(request.getId(), productDao);
//        throwExceptionIfProductIsInUserCart(req.id());

        productDao.deleteProduct(request.getId());
    }

    public ProductDTO.Response searchByName(ProductDTO.Request.GetByStartingName request) {
        List<Product> productsByName = productDao.selectByName(request.getName())
                .orElseThrow(RuntimeException::new);

        return new ProductDTO.Response.Public(productsByName);
    }

    public List<Product> productByCategory(int categoryId) {
        throwExceptionIfCategoryIdDoesNotExist(categoryId, subCategoryDao);

        return productDao.selectByCategoryId(categoryId);
    }

}