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

    String id;
    ArrayList<LineItem> itemsToOrder = new ArrayList<>();
    String status;

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.status = "new";
    }

    public void addLineItem(String id){
        int idToFind = Integer.parseInt(id);
        ProductDao productDataStore = ProductDaoMem.getInstance();
        for (Product product : productDataStore.getAll()){
            if (idToFind == product.getId()){
                itemsToOrder.add(new LineItem(product));
            }
        }
        System.out.println(itemsToOrder);
    }

    public int sumProductsQuantity(){
        return itemsToOrder.size();
    }

    @Override
    public void checkout() {

    }

    @Override
    public void pay() {

    }
}
