package com.codecool.shop.dao;

import com.codecool.shop.customer.Address;
import com.codecool.shop.order.implementation.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

/**
 * Created by annakertesz on 12/6/16.
 */
public interface OrderDao {

    void add(Order order);
    void updateStatus(Order order);
    void updateShippingAddress(Address address, Order order);
    void updateBillingAddress(Address address, Order order);
}
