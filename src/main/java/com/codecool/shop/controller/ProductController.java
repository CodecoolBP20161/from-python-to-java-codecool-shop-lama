package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.order.Order;
import com.codecool.shop.model.Product;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        Order userOrder = req.session().attribute("userOrder");
        Map params = new HashMap<>();
        params.put("suppliers", supplierDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("category", new ProductCategory("All Category", "All Category", "All Category"));
        params.put("supplier", new ProductCategory("All Supplier", "All Supplier", "All Supplier"));
        params.put("cart", userOrder.sumProductsQuantity());

        if (req.queryParams("supId") != null) {
            int supId = Integer.parseInt(req.queryParams("supId"));
            params.put("supplier", supplierDataStore.find(supId));
            params.put("products", productDataStore.getBy(supplierDataStore.find(supId)));
        } else if (req.queryParams("catId") != null) {
            int catId = Integer.parseInt(req.queryParams("catId"));
            params.put("category", productCategoryDataStore.find(catId));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(catId)));
        }

        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderCart(Request req, Response res) {
        Map params = new HashMap<>();
        Order userOrder = req.session().attribute("userOrder");
        params.put("order", userOrder);
        return new ModelAndView(params, "product/shoppingCart");
    }
}

