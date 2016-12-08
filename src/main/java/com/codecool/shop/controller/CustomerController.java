package com.codecool.shop.controller;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.CustomerDaoJdbc;
import com.codecool.shop.model.customer.Customer;
import com.codecool.shop.util.passwordHasher;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import sun.security.util.Password;

import java.util.HashMap;
import java.util.Map;

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

    public static void registration(Request req){
        Customer customer = new Customer(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"));
        CustomerDaoJdbc customerDao = CustomerDaoJdbc.getInstance();
        customerDao.add(customer);
        String salt = passwordHasher.generateSalt();
        String password = passwordHasher.sha256(salt + req.queryParams("password"));
        customerDao.addUser(req.queryParams("userName"), req.queryParams("email"), salt, password, customerDao.find(customer.getCustomerUUID()).getId());
    }

    public static ModelAndView renderRegistration(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/registration");
    }
}
