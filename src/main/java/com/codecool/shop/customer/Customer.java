package com.codecool.shop.customer;

public class Customer {
    private String name;
    private String email;
    private String phoneNumber;
    private Address address;

    public Customer(String name, String email, String phoneNumber, String country, String city, int zipcode, String address){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = new Address(country, city, zipcode, address);
    }

}
