package com.codecool.shop.customer;

public class Address {
    private String country;
    private String city;
    private int zipcode;
    private String address;

    public Address(String country, String city, int zipcode, String address){
        this.country = country;
        this.city = city;
        this.zipcode = zipcode;
        this.address = address;
    }
}
