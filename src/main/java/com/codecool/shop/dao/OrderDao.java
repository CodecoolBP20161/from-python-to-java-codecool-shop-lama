package com.codecool.shop.dao;

import com.codecool.shop.model.customer.Address;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.customer.Customer;
import com.codecool.shop.order.implementation.Order;

/**
 * Created by annakertesz on 12/6/16.
 */
public interface OrderDao {
    void addOrder(Order order);
    void AddLineItem(Order order, Product product);
    void updateStatus(Order order);
    void updateShippingAddress(Address address, Order order);
    void updateBillingAddress(Address address, Order order);
    void updateCustomer(Customer customer, Order order);
    Order find(String orderUUID);
    void removeProductFromOrder(Order order, int productId);
    void changeQuantity(Order order, int productId, int changeBy);
}
