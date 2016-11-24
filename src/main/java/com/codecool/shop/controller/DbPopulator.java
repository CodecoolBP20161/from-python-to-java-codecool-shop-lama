package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

public class DbPopulator {

    public static void populateData() {

        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "phone");
        productCategoryDataStore.add(phone);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "laptop");
        productCategoryDataStore.add(laptop);
        ProductCategory headset = new ProductCategory("Headset", "Hardware", "headset");
        productCategoryDataStore.add(headset);


        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon, "product_1"));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo, "product_3"));
        productDataStore.add(new Product("Lenovo 5520", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", phone, amazon, "product_4"));
        productDataStore.add(new Product("Sony Headset Stuff", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", headset, amazon, "product_5"));
        productDataStore.add(new Product("Apple Ipad", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon, "product_6"));
        productDataStore.add(new Product("HP ProBook", 89, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", laptop, lenovo, "product_7"));
        productDataStore.add(new Product("Nokia 2110", 89,  "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", phone, amazon, "product_8"));
        productDataStore.add(new Product("Asus ZenBook", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, lenovo, "product_9"));
    }
}
