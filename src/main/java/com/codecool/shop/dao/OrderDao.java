package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.order.implementation.Order;

/**
 * Created by annakertesz on 12/6/16.
 */
public interface OrderDao {

    void addOrder(Order order);
    public void AddLineItem(Order order, Product product);

}
