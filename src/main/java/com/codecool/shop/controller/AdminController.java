package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoJdbc;
import com.codecool.shop.order.implementation.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by annakertesz on 1/19/17.
 */
public class AdminController {

    private static OrderDao orderDao = OrderDaoJdbc.getInstance();

    public static ModelAndView renderAdminPage(Request req, Response res) {
        Map params = new HashMap<>();
        String jsonDatas = "[";
        for (Order order : orderDao.getAll()) {
            jsonDatas += "{name: " + order.getCustomer().getName();
            jsonDatas += ", city: " + order.getShippingAddress().getCity();
            jsonDatas += ", address: " + order.getShippingAddress().getAddress();
            jsonDatas += ", id: " + order.getId() + "},";
        }
        jsonDatas = jsonDatas.substring(0, jsonDatas.length()-1);
        jsonDatas += "]";
        params.put("orders", orderDao.getAll());
        params.put("jsonDatas", jsonDatas);
        System.out.println(jsonDatas);
        return new ModelAndView(params, "product/admin");

    }

}
