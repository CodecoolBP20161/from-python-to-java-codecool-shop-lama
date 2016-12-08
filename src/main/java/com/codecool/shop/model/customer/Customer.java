package com.codecool.shop.model.customer;

import java.util.UUID;

public class Customer {
    private int id;
    private String customerUUID;
    private String name;
    private String email;
    private String phoneNumber;
    private Address billingAddress;
    private Address shippingAddress;

    public Customer(String name, String email, String phoneNumber,
                    String billingCountry, String billingCity, String billingZipcode, String billingAddress,
                    String shippingCountry, String shippingCity, String shippingZipcode, String shippingAddress){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = new Address(billingCountry, billingCity, billingZipcode, billingAddress);
        this.shippingAddress = new Address(shippingCountry, shippingCity, shippingZipcode, shippingAddress);
        this.customerUUID = UUID.randomUUID().toString();
    }

    public Customer(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerUUID = UUID.randomUUID().toString();
    }

    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getEmail(){
        return email;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCustomerUUID() {
        return customerUUID;
    }

    public void setCustomerUUID(String customerUUID) {
        this.customerUUID = customerUUID;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }
}
