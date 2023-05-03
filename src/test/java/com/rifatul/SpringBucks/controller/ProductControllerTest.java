package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductControllerTest {

    @Autowired
    private ProductService productService;

    @Test
    public void Get_product_by_id_gives_status_OK() {

    }

    @Test
    @DisplayName("get product by not existing id outputs status not found")
    public void get_product_by_not_existing_id_outputs_status_not_found() {

    }

    @Test
    @DisplayName("get product by starting name outputs status OK")
    public void t() {

    }

    @Test
    @DisplayName("get product by starting name that does not exist outputs status OK")
    public void t1() {

    }

    @Test
    @DisplayName("get product by not existing category_id outputs status NOT FOUND")
    public void t3() {

    }

    @Test
    @DisplayName("get product by category_id outputs status OK")
    public void t4() {

    }

    @Test
    @DisplayName("update product price outputs status OK")
    public void t5() {

    }

    @Test
    @DisplayName("update product not existing category_id outputs status NOT FOUND")
    public void t12() {

    }

    @Test
    @DisplayName("update product price in incorrect format outputs status NOT CONFIDENTIAL")
    public void t21() {

    }

    @Test
    @DisplayName("update product price less than zero outputs status NOT CONFIDENTIAL")
    public void t1wdw() {

    }

    @Test
    @DisplayName("add product outputs status OK")
    public void t112() {

    }

    @Test
    @DisplayName("add product using existing name outputs status CONFLICT")
    public void t1122() {

    }

    @Test
    @DisplayName("add product with price less than zero outputs status NOT CONFIDENTIAL")
    public void t1d2wd() {

    }
}