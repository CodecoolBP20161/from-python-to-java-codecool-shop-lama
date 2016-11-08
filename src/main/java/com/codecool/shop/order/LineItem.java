package com.codecool.shop.order;

import com.codecool.shop.model.Product;

/**
 * Created by annakertesz on 11/8/16.
 */
public class LineItem {
    private Product product;
    private int quantity;
    private float price;

    public LineItem(){}

    public LineItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.price = this.product.getPriceFloat() * quantity;

    }
}
