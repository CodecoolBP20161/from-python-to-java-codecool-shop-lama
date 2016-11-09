package com.codecool.shop.order;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by annakertesz on 11/8/16.
 */
public class Order implements Orderable {

    private String id;
    private ArrayList<LineItem> itemsToOrder;
    private String status;

    {
        itemsToOrder = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.status = "new";
    }

    public Order() {
    }

    public ArrayList<LineItem> getItemsToOrder(){
        return itemsToOrder;
    }

    public void addLineItem(int id){
        ProductDao productDataStore = ProductDaoMem.getInstance();
        for (Product product : productDataStore.getAll()){
            if (id == product.getId()) addItemToOrder(product);
        }
    }

    private void addItemToOrder(Product product){
        for (LineItem lineItem : itemsToOrder) {
            if (lineItem.getProduct().equals(product)){
                lineItem.increaseQuantity();
                return;
            }
        }
        itemsToOrder.add(new LineItem(product));
    }

    public int sumProductsQuantity(){
        int sumQuantity = 0;
        for (LineItem anItemsToOrder: itemsToOrder) {
            sumQuantity += anItemsToOrder.getQuantity();
        }
        return sumQuantity;
    }

    public float sumProductsPrice(){
        float sumPrice = 0;
        for (LineItem anItemsToOrder : itemsToOrder) {
            sumPrice += anItemsToOrder.getPrice();
        }
        return sumPrice;
    }

    @Override
    public void checkout() {

    }

    @Override
    public void pay() {

    }
}
