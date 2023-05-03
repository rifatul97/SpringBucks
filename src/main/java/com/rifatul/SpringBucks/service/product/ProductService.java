package com.rifatul.SpringBucks.service.product;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.domain.dto.CategoriesDto;
import com.rifatul.SpringBucks.domain.dto.ProductDTO;
import com.rifatul.SpringBucks.domain.model.Category;
import com.rifatul.SpringBucks.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.service.product.ProductValidatorService.*;

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

        log.info("yes we are saving this {}", req.toString());
        productDao.save(req.name(), req.price(), req.categoryId());
    }

    public void update (ProductDTO.Request.Update req) throws InterruptedException {
        System.out.println("1");
        throwExceptionIfProductIdDoesNotExist(req.id(), productDao);
        System.out.println("2");
        throwExceptionIfCategoryIdDoesNotExist(req.categoryId(), subCategoryDao);
        System.out.println("3");
        throwExceptionIfPriceIsIncorrectFormat(req.price());
        System.out.println("4");
        throwExceptionIfProductHadBeenOrdered(req.id(), orderDao, cartDao);
        System.out.println("5");

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

    public List<Product> productByParentCategory(int categoryId) {
        throwExceptionIfCategoryIdDoesNotExist(categoryId, subCategoryDao);
//        throwExceptionIfParentCategoryIdDoesNotExist(categoryId, subCategoryDao);
        List<Category> subCategoriesByParentCategoryId = subCategoryDao.selectByParentId(categoryId);
        List<Product> products = new ArrayList<>();

        for (Category subCategory : subCategoriesByParentCategoryId) {
            products.addAll(productByCategory(subCategory.id()));
        }

        return products;
    }

    public Optional<Product> getById(int id) {
        return productDao.selectById(id);
    }

    public List<Product> getByCategories(List<CategoriesDto> categories) {
        List<Product> allProducts = new ArrayList<>();

        for (CategoriesDto categorydto : categories) {
            for (Category childCategory : categorydto.child()) {
                allProducts.addAll(this.productByCategory(childCategory.id()));
            }
        }

        return allProducts;
    }
}