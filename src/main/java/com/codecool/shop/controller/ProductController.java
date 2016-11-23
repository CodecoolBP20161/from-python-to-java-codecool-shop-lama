package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.order.implementation.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) throws SQLException {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();
        Order userOrder = req.session().attribute("userOrder");

        Map params = new HashMap<>();
        params.put("suppliers", supplierDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("category", new ProductCategory("All Category", "All Category", "All Category"));
        params.put("supplier", new ProductCategory("All Supplier", "All Supplier", "All Supplier"));
        if (userOrder != null) params.put("cart", userOrder.sumProductsQuantity());

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
        params.put("order", req.session().attribute("userOrder"));
        return new ModelAndView(params, "product/shoppingCart");
    }

    public static ModelAndView renderCheckout(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/checkout");
    }

    public static ModelAndView renderPayment(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/payment");
    }
}

