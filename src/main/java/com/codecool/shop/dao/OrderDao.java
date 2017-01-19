package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.customer.Customer;
import com.codecool.shop.order.implementation.Order;

import java.util.ArrayList;

/**
 * Created by annakertesz on 12/6/16.
 */
public interface OrderDao {
    void addOrder(Order order);
    void AddLineItem(Order order, Product product);
    void updateStatus(Order order);
    void updateShippingAddress(int addressId, Order order);
    void updateBillingAddress(int addressId, Order order);
    void updateCustomer(Customer customer, Order order);
    void updateAddress();
    Order find(String orderUUID);
    void removeProductFromOrder(Order order, int productId);
    void changeQuantity(Order order, int productId, int changeBy);
    public ArrayList<Order> getAll();
}
