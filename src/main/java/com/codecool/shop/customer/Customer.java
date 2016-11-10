package com.codecool.shop.customer;

public class Customer {
    private String name;
    private String email;
    private String phoneNumber;
    private Address billingAddress;
    private Address shippingAddress;

    public Customer(String name, String email, String phoneNumber,
                    String bCountry, String bCity, int bZipcode, String bAddress,
                    String sCountry, String sCity, int sZipcode, String sAddress){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = new Address(bCountry, bCity, bZipcode, bAddress);
        this.shippingAddress = new Address(sCountry, sCity, sZipcode, sAddress);
    }

}
