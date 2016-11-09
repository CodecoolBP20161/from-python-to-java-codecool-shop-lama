package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.order.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        Map params = new HashMap<>();
        params.put("supplier", supplierDataStore.getAll());
        params.put("category", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getAll());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderFilteredProductsByCategory(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        int id = Integer.parseInt(req.queryParams("id"));
        Map params = new HashMap<>();
        params.put("supplier", supplierDataStore.getAll());
        params.put("category", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderFilteredProductsBySupplier(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        int id = Integer.parseInt(req.queryParams("id"));
        Map params = new HashMap<>();
        params.put("supplier", supplierDataStore.getAll());
        params.put("category", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getBy(supplierDataStore.find(id)));
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderCart(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        Map params = new HashMap<>();
        Order anyad = new Order();
        List<Product> products = productDataStore.getAll();
        for (int i=0; i<products.size(); i++){
            anyad.addLineItem(products.get(i));
        }
        params.put("order", anyad.getItemsToOrder());
        return new ModelAndView(params, "product/shoppingCart");
    }
}

