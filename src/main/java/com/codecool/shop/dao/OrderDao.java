package com.codecool.shop.dao;

import com.codecool.shop.customer.Address;
import com.codecool.shop.model.Product;
import com.codecool.shop.order.implementation.Order;

/**
 * Created by annakertesz on 12/6/16.
 */
public interface OrderDao {

    void addOrder(Order order);
    public void AddLineItem(Order order, Product product);
    public void updateStatus(Order order);
    public void updateShippingAddress(Address address, Order order);
    public void updateBillingAddress(Address address, Order order);

}
