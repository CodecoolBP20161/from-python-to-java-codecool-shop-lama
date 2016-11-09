package com.codecool.shop.order;

import com.codecool.shop.model.Product;

/**
 * Created by annakertesz on 11/8/16.
 */
public class LineItem {
    private String name;
    private Product product;
    private int quantity;
    private float price_db;
    private float price;

    public LineItem(){}

    public LineItem(Product product) {
        this.product = product;
        this.name = product.getName();
        this.quantity = 1;
        this.price_db = product.getPriceFloat();
        this.price = this.product.getPriceFloat() * quantity;

    }

    public float getPrice(){
        return price;
    }

    public float getPrice_db(){
        return price_db;
    }

    public String getName(){
        return name;
    }

    public int getQuantity(){
        return quantity;
    }

    public Product getProduct(){
        return product;
    }
}
