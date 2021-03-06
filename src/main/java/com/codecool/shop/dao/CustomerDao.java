package com.codecool.shop.dao;

import com.codecool.shop.model.customer.Customer;

import java.util.List;

/**
 * Created by leviathan on 2016.12.07..
 */
public interface CustomerDao {
    void add(Customer customer);
    Customer find(int id);
    Customer find(String customerUUID);
    void remove(int id);
    public int getBillingAddressId(String uuid);
    public int getShippingAddressId(String uuid);
    public int getCustomerId(String userName);

    List<Customer> getAll();

}
