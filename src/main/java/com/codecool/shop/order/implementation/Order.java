package com.codecool.shop.order.implementation;

import com.codecool.shop.customer.Customer;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.order.Orderable;
import jdk.nashorn.internal.ir.RuntimeNode;
import spark.Request;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by annakertesz on 11/8/16.
 */
public class Order implements Orderable {

    private String id;
    private ArrayList<LineItem> itemsToOrder;
    private String status;
    private Customer customer;

    {
        itemsToOrder = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.status = "new";
    }

    public Order() {
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public void changeQuantity(String id, int dif) {
        int idToFind = Integer.parseInt(id);
        for (LineItem item : itemsToOrder) {
            if (idToFind == item.getProduct().getId()) {
                item.setQuantity(dif);
                if (item.getQuantity() == 0) itemsToOrder.remove(item);
                break;
            }
        }
    }

    public void removeItem(String id){
        int idToFind = Integer.parseInt(id);
        for (LineItem item : itemsToOrder){
            if (idToFind == item.getProduct().getId()){
                itemsToOrder.remove(item);
                break;
            }
        }
        System.out.println(itemsToOrder);
    }

    public int sumProductsQuantity(){
        int sumQuantity = 0;
        for (LineItem anItemsToOrder: itemsToOrder) {
            sumQuantity += anItemsToOrder.getQuantity();
        }
        return sumQuantity;
    }

    public String sumProductsPrice(){
        float sumPrice = 0;
        for (LineItem anItemsToOrder : itemsToOrder) {
            sumPrice += Float.parseFloat(anItemsToOrder.getPrice());
        }
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(sumPrice);
    }

    @Override
    public void checkout() {
        if (Objects.equals(status, "new")) status = "checked";
    }

    @Override
    public void pay() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public String getStatus() {
        return status;
    }
}
