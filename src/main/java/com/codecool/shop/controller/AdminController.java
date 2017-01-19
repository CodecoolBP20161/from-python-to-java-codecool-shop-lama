package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoJdbc;
import com.codecool.shop.delivery.RoutePlanner;
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
        params.put("orders", orderDao.getAll());
        params.put("calculation", getRoutes());
        params.put("jsonDatas", createJson(all));
        return new ModelAndView(params, "product/admin");
    }



    private static ArrayList<String> createAddressList(ArrayList<Order> all){
        ArrayList<String> addressList = new ArrayList<>();
        for (Order order: all){
            String address = order.getShippingAddress().getCity();
            if(!addressList.contains(address)) addressList.add(address);
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

    public static ArrayList<String> getRoutes() {
        RoutePlanner planner = new RoutePlanner(createAddressList(orderDao.getAll()));
        System.out.println(planner.toString());
        return planner.findSolution();
    }

}
