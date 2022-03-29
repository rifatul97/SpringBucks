package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.exception.CategoryDoesNotExistException;
import com.rifatul.SpringBucks.exception.IncorrectPriceFormatException;
import com.rifatul.SpringBucks.exception.ProductNameAlreadyExistException;
import com.rifatul.SpringBucks.exception.ProductNotExistingException;

import java.math.BigDecimal;

public class ProductValidatorService {

    public static void throwExceptionIfCategoryIdDoesNotExist(int categoryId, SubCategoryDao subCategoryDao) {
        if (subCategoryDao.selectById(categoryId).isEmpty()) {
            throw new CategoryDoesNotExistException(categoryId);
        }
    }

    public static void throwExceptionIfPriceIsIncorrectFormat(Double price) {
//        System.out.println("scale = " + BigDecimal.valueOf(price).scale());
//        System.out.println(BigDecimal.valueOf(price).toPlainString().length());
//        if (BigDecimal.valueOf(price).toPlainString().length() != 4) {
//
//            throw new IncorrectPriceFormatException("price cannot exceed the length of 4");
//        }
//        if (BigDecimal.valueOf(price).scale() != 2) {
//            throw new IncorrectPriceFormatException("price format should ##.##");
//        }
        if (price <= 0) {
            throw new IncorrectPriceFormatException("price cannot be less or equal to 0");
        }
    }

    public static void throwExceptionIfProductIdDoesNotExist(int productId, ProductDao productDao) {
        if (productDao.selectById(productId).isEmpty()) {
            throw new ProductNotExistingException(productId);
        }
    }

    public static void throwExceptionIfProductNameAlreadyExist(String name, ProductDao productDao) {
        if (!productDao.selectByName(name).isEmpty()) {
            throw new ProductNameAlreadyExistException(name);
        }
    }
}