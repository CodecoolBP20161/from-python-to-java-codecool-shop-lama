package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.customer.Customer;

import java.util.List;

/**
 * Created by leviathan on 2016.12.07..
 */
public interface CustomerDao {
    void add(Customer customer);
    Customer find(int id);
    void remove(int id);

    List<Customer> getAll();

}
