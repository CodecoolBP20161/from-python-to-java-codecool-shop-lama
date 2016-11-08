package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.order.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        Map params = new HashMap<>();
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("category", new ProductCategory("All Products", "All Products", "All Products"));
        params.put("products", productDataStore.getAll());
        params.put("chart", Order.sumProductsQuantity());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderFilteredProductsByCategory(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        int id = Integer.parseInt(req.queryParams("id"));
        Map params = new HashMap<>();
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("category", productCategoryDataStore.find(id));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        params.put("chart", Order.sumProductsQuantity());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderFilteredProductsBySupplier(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        int id = Integer.parseInt(req.queryParams("id"));
        Map params = new HashMap<>();
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("category", supplierDataStore.find(id));
        params.put("products", productDataStore.getBy(supplierDataStore.find(id)));
        params.put("chart", Order.sumProductsQuantity());
        return new ModelAndView(params, "product/index");
    }
}

