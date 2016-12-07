package com.codecool.shop.model.customer;

public class Customer {
    private int id;
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
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getBillingAddressID() { return billingAddress.getId(); }
    public int getShippingAddressID() { return shippingAddress.getId(); }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
