package com.codecool.shop.model.customer;

public class Address {
    private int id;
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

    public String getCountry(){ return country; }
    public String getCity(){ return city; }
    public String getZipcode(){ return zipcode; }
    public String getAddress(){ return address; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
