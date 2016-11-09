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

    public void addLineItem(String id){
        int idToFind = Integer.parseInt(id);
        ProductDao productDataStore = ProductDaoMem.getInstance();
        int where = this.containsLineItem(idToFind);
        for (Product product : productDataStore.getAll()){
            if (idToFind == product.getId()){
                if (where == -1){
                    itemsToOrder.add(new LineItem(product));
                }
                else{
                    itemsToOrder.get(where).increaseQuantity();
                }
            }
        }
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
        for (LineItem anItemsToOrder : itemsToOrder) {
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

    private int containsLineItem(int id){
        for (int i=0; i<itemsToOrder.size();i++){
            if (itemsToOrder.get(i).getId() == id)
                return i;
        }
        return -1;
    }

    public ArrayList<LineItem> getItemsToOrder(){
        return itemsToOrder;
    }

    @Override
    public void checkout() {

    }

    @Override
    public void pay() {

    }
}
