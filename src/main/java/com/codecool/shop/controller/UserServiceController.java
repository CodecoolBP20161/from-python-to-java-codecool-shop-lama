package com.codecool.shop.controller;

import org.apache.http.client.utils.URIBuilder;

import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by cave on 2016.12.08..
 */
public class UserServiceController {
    private static final String SERVICE_URL = "http://localhost:60000";



    public static String loginValidation(String userName, String password) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(SERVICE_URL + "/api/validate-user");
        uriBuilder.addParameter("user_name", userName);
        uriBuilder.addParameter("password", password);
        return execute(uriBuilder);
    }

    private static String execute(URIBuilder uriBuilder) throws URISyntaxException, IOException {
        return Request.Get(uriBuilder.build()).execute().returnContent().asString();
    }
}
