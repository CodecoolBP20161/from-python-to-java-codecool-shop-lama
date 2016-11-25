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
        Supplier sony = new Supplier("Sony", "Electronics");
        supplierDataStore.add(sony);
        Supplier apple = new Supplier("Apple", "Electronics without jack");
        supplierDataStore.add(apple);
        Supplier hp = new Supplier("HP", "Hewlett-Packard");
        supplierDataStore.add(hp);
        Supplier nokia = new Supplier("nokia", "Used to manufacture boots for lumberjacks");
        supplierDataStore.add(nokia);
        Supplier asus = new Supplier("asus", "Meh");
        supplierDataStore.add(asus);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", " A speech sound considered as a physical event without regard to its place in the sound system of a language.");
        productCategoryDataStore.add(phone);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A portable computer, usually battery-powered, small enough to rest on the user's lap and having a screen that closes over the keyboard like a lid.");
        productCategoryDataStore.add(laptop);
        ProductCategory headset = new ProductCategory("Headset", "Hardware", "A device consisting of one or two earphones with a headband for holding them over the ears and sometimes with a mouthpiece attached.");
        productCategoryDataStore.add(headset);


        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon, "product_1"));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo, "product_2"));
        productDataStore.add(new Product("Lenovo 5520", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", phone, lenovo, "product_3"));
        productDataStore.add(new Product("Sony Headset Stuff", 25, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", headset, sony, "product_4"));
        productDataStore.add(new Product("Apple Ipad", 899, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, apple, "product_5"));
        productDataStore.add(new Product("HP ProBook", 659, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", laptop, hp, "product_6"));
        productDataStore.add(new Product("Nokia 2110", 1,  "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", phone, nokia, "product_7"));
        productDataStore.add(new Product("Asus ZenBook", 1099, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, asus, "product_8"));
    }
}
