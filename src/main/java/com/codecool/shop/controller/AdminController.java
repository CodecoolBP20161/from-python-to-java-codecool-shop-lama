package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoJdbc;
import com.codecool.shop.order.implementation.Order;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by annakertesz on 1/19/17.
 */
public class AdminController {

    private static OrderDao orderDao = OrderDaoJdbc.getInstance();

    public static ModelAndView renderAdminPage(Request req, Response res) {
        Map params = new HashMap<>();
        ArrayList<Order> all = orderDao.getAll();
//dgdf
        params.put("orders", orderDao.getAll());
        params.put("jsonDatas", createJson(all));
        return new ModelAndView(params, "product/admin");

    }

    private ArrayList<String> createAddressList(ArrayList<Order> all){
        ArrayList<String> addressList = new ArrayList<>();
        for (Order order: all){
            String address = order.getShippingAddress().getZipcode() + " ";
            address += order.getShippingAddress().getCity() + " ";
            address += order.getShippingAddress().getAddress();
            addressList.add(address);
        }
        return addressList;
    }

    private static String createJson(ArrayList<Order> all) {
        String jsonDatas = "[";
        for (Order order : all) {
            jsonDatas += "{name: " + order.getCustomer().getName();
            jsonDatas += ", city: " + order.getShippingAddress().getCity();
            jsonDatas += ", address: " + order.getShippingAddress().getAddress();
            jsonDatas += ", id: " + order.getId() + "},";
        }
        jsonDatas = jsonDatas.substring(0, jsonDatas.length()-1);
        jsonDatas += "]";
        return jsonDatas;
    }

}
