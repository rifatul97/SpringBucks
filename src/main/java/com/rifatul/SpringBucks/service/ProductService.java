package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.domain.dto.ProductDTO;
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
    @Autowired private final CartDao cartDao;
    @Autowired private final OrderDao orderDao;

    public void save(ProductDTO.Request.Create req) {
        throwExceptionIfCategoryIdDoesNotExist(req.categoryId(), subCategoryDao);
        throwExceptionIfPriceIsIncorrectFormat(req.price());
        throwExceptionIfProductNameAlreadyExist(req.name(), productDao);

        productDao.save(req.name(), req.price(), req.categoryId());
    }

    public void update (ProductDTO.Request.Update req) throws InterruptedException {
        throwExceptionIfProductIdDoesNotExist(req.id(), productDao);
        throwExceptionIfCategoryIdDoesNotExist(req.categoryId(), subCategoryDao);
        throwExceptionIfPriceIsIncorrectFormat(req.price());
        throwExceptionIfProductHadBeenOrdered(req.id(), orderDao, cartDao);

        productDao.updateProduct(req.id(), req.categoryId(), req.price());
    }

    public void delete (ProductDTO.Request.Delete request) throws InterruptedException {
        throwExceptionIfProductIdDoesNotExist(request.id(), productDao);
        throwExceptionIfProductHadBeenOrdered(request.id(), orderDao, cartDao);

        productDao.deleteProduct(request.id());
    }

    public List<Product> searchByName(String name) {

        return productDao.selectByName(name)
                .orElseThrow(RuntimeException::new);
    }

    public List<Product> productByCategory(int categoryId) {
        throwExceptionIfCategoryIdDoesNotExist(categoryId, subCategoryDao);

        return productDao.selectByCategoryId(categoryId);
    }

}