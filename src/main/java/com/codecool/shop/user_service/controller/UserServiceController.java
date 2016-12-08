package com.codecool.shop.user_service.controller;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.CustomerDaoJdbc;
import com.codecool.shop.email.EmailSender;
import com.codecool.shop.model.customer.Customer;
import com.codecool.shop.util.passwordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import javax.mail.MessagingException;

/**
 * Created by cave on 2016.12.08..
 */
public class UserServiceController {

    public boolean loginValidation(Request req, Response resp) {
        CustomerDaoJdbc customerDao = CustomerDaoJdbc.getInstance();
        String userName = req.queryParams("user_name");
        String salt = customerDao.getSalt(userName);
        String passwordInput = passwordHasher.sha256(salt + req.queryParams("password"));
        if (customerDao.loginValidation(userName, passwordInput)) {
            return true;
        }
        return false;
    }
}
