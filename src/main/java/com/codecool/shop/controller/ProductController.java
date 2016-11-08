package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

//    private static ModelAndView renderAllProducts(Request req, Response res) {
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.getAll());
//        params.put("products", productDataStore.getAll());
//        return new ModelAndView(params, "product/index");
//    }

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        String idStr = req.queryParams("id");
        Map params = new HashMap<>();
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        }
        else {params.put("products", productDataStore.getAll());}

        params.put("category", productCategoryDataStore.getAll());

        return new ModelAndView(params, "product/index");
    }
}

