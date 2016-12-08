package com.codecool.shop.controller;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.CustomerDaoJdbc;
import com.codecool.shop.email.EmailSender;
import com.codecool.shop.model.customer.Customer;
import com.codecool.shop.util.passwordHasher;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import sun.security.util.Password;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leviathan on 2016.12.07..
 */
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

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
        sendMail(customer);
    }

    public static ModelAndView renderRegistration(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/registration");
    }

    private static String sendMail(Customer customer) {
        EmailSender emailSender = EmailSender.getInstance();

        try {
            emailSender.sendRegistrationEmail(customer);
        } catch (MessagingException e) {
            return "Email sending has failed";
        }
        return "Email sending was successful";
    }

    public static boolean loginValidation(Request request, Response response) {
        String userName = request.queryParams("user_name");
        String password = request.queryParams("password");
        logger.debug("user_name: " + userName);
        try {
            if (UserServiceController.loginValidation(userName, password).equals("true")) {
                logger.info("login successful");

                CustomerDao customerDao = CustomerDaoJdbc.getInstance();
                Customer customer = customerDao.find(customerDao.getCustomerId(userName));
                request.session().attribute("logged-in-user", customer);
                System.out.println(request.session().attribute("logged-in-user").toString());
                return true;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String logout(Request request, Response response) {
        request.session().attribute("logged-in-user", null);
        return "logged out";
    }
}
