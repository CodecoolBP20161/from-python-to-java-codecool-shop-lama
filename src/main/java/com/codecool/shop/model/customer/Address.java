package com.codecool.shop.model.customer;

public class Address {
    private String country;
    private String city;
    private String zipcode;
    private String address;

    public Address(String country, String city, String zipcode, String address){
        this.country = country;
        this.city = city;
        this.zipcode = zipcode;
        this.address = address;
    }
}
