package com.codecool.shop.order.implementation;

import com.codecool.shop.customer.Customer;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.Status;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.order.Orderable;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Order implements Orderable {

    private int id;
    private ArrayList<LineItem> itemsToOrder;
    private Status status;
    private Customer customer;

    {
        itemsToOrder = new ArrayList<>();


        this.status = Status.NEW;
    }

    public Order() {
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<LineItem> getItemsToOrder(){
        return itemsToOrder;
    }

    public void addLineItem(int id) throws SQLException {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        for (Product product : productDataStore.getAll()){
            if (id == product.getId()) addItemToOrder(product);
        }
    }

    private void addItemToOrder(Product product){
        for (LineItem lineItem : itemsToOrder) {
            if (lineItem.getProduct().getId() == product.getId()){
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
        if (Objects.equals(status, Status.NEW)) status = Status.CHECKED;
    }

    @Override
    public void pay() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public Status getStatus() {
        return status;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getID() {
        return id;
    }
}
