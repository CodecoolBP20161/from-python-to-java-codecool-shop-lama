package com.codecool.shop.controller;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.CustomerDaoJdbc;
import com.codecool.shop.model.customer.Customer;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoJdbc;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.order.CheckoutProcess;
import com.codecool.shop.order.implementation.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OrderController {
    private static OrderDao orderDao = OrderDaoJdbc.getInstance();
    private static CustomerDao customerDao = CustomerDaoJdbc.getInstance();
    private static ProductDao productDao = ProductDaoJdbc.getInstance();

    public static void addProductToCart(Request req) throws SQLException {
        // available session check
        makeSessionOrderIfNecessary(req);
        Order userOrder = req.session().attribute("userOrder");
        int productId = Integer.parseInt(req.queryParams("id"));
        userOrder.addLineItem(productId);
        orderDao.AddLineItem(userOrder, productDao.find(productId));

    }

    public static boolean checkOut(Request req) {
        CheckoutProcess checkoutProcess = new CheckoutProcess();
        Order order = req.session().attribute("userOrder");
        if (order == null) return false;
        Customer customer = CustomerController.makeNewCustomer(req);
        order.setCustomer(customer);
        customerDao.add(customer);
        Customer customerInDb = customerDao.find(customer.getCustomerUUID());
        checkoutProcess.action(order);
        orderDao.updateStatus(order);
        orderDao.updateCustomer(customerDao.find(customer.getCustomerUUID()), order);
        orderDao.updateBillingAddress(customerDao.getBillingAddressId(customer.getCustomerUUID()), order);
        orderDao.updateShippingAddress(customerDao.getShippingAddressId(customer.getCustomerUUID()), order);
        return true;
    }

    // rendering cart.html template
    public static ModelAndView renderCart(Request req, Response res) {
        // available session check
        if (req.session().attribute("userOrder") == null) res.redirect("/");
        Map params = new HashMap<>();
        params.put("order", req.session().attribute("userOrder"));
        return new ModelAndView(params, "product/shoppingCart");
    }

    private static void makeSessionOrderIfNecessary(Request req) {
        if (req.session().attribute("userOrder") == null) {
            Order order = new Order();
            orderDao.addOrder(order);
            req.session().attribute("userOrder", orderDao.find(order.getOrderUUID()));
        }
    }

    public static ModelAndView renderCheckout(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/checkout");
    }

    public static ModelAndView renderPayment(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/payment");
    }

    public static void removeProductFromOrder(Request req, Response res) {
        Order order = req.session().attribute("userOrder");
        order.removeItem(req.queryParams("id"));
        orderDao.removeProductFromOrder(order, Integer.parseInt(req.queryParams("id")));
    }

    public static void changeQuantity(Request req, Response res, int changeBy) {
        Order order = req.session().attribute("userOrder");
        order.changeQuantity(req.queryParams("id"), changeBy);
        orderDao.changeQuantity(order, Integer.parseInt(req.queryParams("id")), changeBy);
    }
}
