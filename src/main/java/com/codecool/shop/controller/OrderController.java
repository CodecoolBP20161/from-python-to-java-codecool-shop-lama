package com.codecool.shop.controller;

import com.codecool.shop.customer.Customer;
import com.codecool.shop.order.CheckoutProcess;
import com.codecool.shop.order.implementation.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cave on 2016.11.10..
 */
public class OrderController {

    public static void addProductToCart(Request req) {
        // available session check
        makeSessionOrderIfNecessary(req);
        Order userOrder = req.session().attribute("userOrder");
        userOrder.addLineItem(Integer.parseInt(req.queryParams("id")));
    }

    public static boolean checkOut(Request req) {
        CheckoutProcess checkoutProcess = new CheckoutProcess();
        Order order = req.session().attribute("userOrder");
        if (order == null) return false;
        order.setCustomer(makeNewCustomer(req));
        checkoutProcess.action(order);
        return true;
    }

    private static Customer makeNewCustomer(Request req) {
        return new Customer(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"),
                req.queryParams("billingCountry"), req.queryParams("billingCity"),
                req.queryParams("billingZipcode"), req.queryParams("billingAddress"),
                req.queryParams("shippingCountry"), req.queryParams("shippingCity"),
                req.queryParams("shippingZipcode"), req.queryParams("shippingAddress"));
    }

    // rendering cart.html template
    public static ModelAndView renderCart(Request req, Response res) {
        // available session check
        makeSessionOrderIfNecessary(req);
        Map params = new HashMap<>();
        params.put("order", req.session().attribute("userOrder"));
        return new ModelAndView(params, "product/shoppingCart");
    }

    private static void makeSessionOrderIfNecessary(Request req) {
        if (req.session().attribute("userOrder") == null) {
            req.session().attribute("userOrder", new Order());
        }
    }

    public static ModelAndView renderCheckout(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/checkout");
    }
}
