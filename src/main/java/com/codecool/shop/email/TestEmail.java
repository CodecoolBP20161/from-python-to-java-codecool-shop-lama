package com.codecool.shop.email;

import com.codecool.shop.model.customer.Customer;

/**
 * Created by prezi on 2016. 12. 08..
 */
public class TestEmail {
    public static void main(String[] args){
        Customer misi = new Customer("Misi", "mihalyfuredi@gmail.com", "+36202608281");
        SendEmail email =  SendEmail.getInstance();
        email.sendRegistrationEmail(misi);
    }
}
