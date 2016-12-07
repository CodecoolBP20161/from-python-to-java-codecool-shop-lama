package com.codecool.shop.controller;

import com.codecool.shop.model.customer.Customer;
import spark.Request;

/**
 * Created by leviathan on 2016.12.07..
 */
public class CustomerController {
    static Customer makeNewCustomer(Request req) {
        if (req.queryParams("billingCountry").equals(null)){
            return new Customer(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"));
        }
        return new Customer(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"),
                req.queryParams("billingCountry"), req.queryParams("billingCity"),
                req.queryParams("billingZipcode"), req.queryParams("billingAddress"),
                req.queryParams("shippingCountry"), req.queryParams("shippingCity"),
                req.queryParams("shippingZipcode"), req.queryParams("shippingAddress"));
    }
}
