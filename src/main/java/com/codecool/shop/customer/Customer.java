package com.codecool.shop.customer;

public class Customer {
    private String name;
    private String email;
    private String phoneNumber;
    private Address billingAddress;
    private Address shippingAddress;

    public Customer(String name, String email, String phoneNumber,
                    String billingCountry, String billinCity, int billingZipcode, String billingAddress,
                    String shippingCountry, String shippingCity, int shippingZipcode, String shippingAddress){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = new Address(billingCountry, billinCity, billingZipcode, billingAddress);
        this.shippingAddress = new Address(shippingCountry, shippingCity, shippingZipcode, shippingAddress);
    }

}
