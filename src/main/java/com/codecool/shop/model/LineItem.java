package com.codecool.shop.model;

import com.codecool.shop.model.Product;

/**
 * Created by annakertesz on 11/8/16.
 */
public class LineItem {
    private int id;
    private String name;
    private Product product;
    private int quantity;
    private float price;

    public LineItem(){}

    public LineItem(Product product) {
        this.id = product.getId();
        this.product = product;
        this.name = product.getName();
        this.quantity = 1;
        this.price = product.getPriceFloat();

    }

    public int getId(){
        return id;
    }

    public float getPrice(){
        //sum
        return price*quantity;
    }

    public float getPrice_db(){
        return price;
    }

    public String getName(){
        return name;
    }

    public int getQuantity(){
        return quantity;
    }

    public void increaseQuantity(){
        quantity++;
    }

    public void setQuantity(int difference) {
        this.quantity += difference;
    }

    public Product getProduct(){
        return product;
    }
}
