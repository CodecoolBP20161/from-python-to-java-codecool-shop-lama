package com.codecool.shop.model;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.codecool.shop.model.Product;

/**
 * Created by annakertesz on 11/8/16.
 */
public class LineItem {
    private int id;
    private Product product;
    private int quantity;

    public LineItem(){}

    public LineItem(Product product) {
        this.id = product.getId();
        this.product = product;
        this.quantity = 1;

    }

    public int getId(){
        return id;
    }

    public String getPrice(){
        //sum
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(product.getPriceFloat()*quantity);
    }

    public String getPrice_db(){
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(product.getPriceFloat());
    }

    public String getName(){
        return product.getName();
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
