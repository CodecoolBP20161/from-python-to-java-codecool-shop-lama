package com.codecool.shop.email;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by prezi on 2016. 12. 08..
 */
public class SMTPConnection {
    private static final String propertiesFilePath = "src/main/resources/properties/smtp_server_settings.properties";
    private static SMTPConnection INSTANCE;
    private static String user;
    private static String password;
    private static String host;
    private static String port;


    public static SMTPConnection getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new SMTPConnection(propertiesFilePath);
        }
        return INSTANCE;
    }

    private SMTPConnection(String propertiesFilePath){
        Properties properties = new Properties();
        InputStream input = null;

        // get the properties from the properties file
        try {
            input = new FileInputStream(propertiesFilePath);
            try {
                properties.load(input);
                user = properties.getProperty("user");
                host = properties.getProperty("host");
                port = properties.getProperty("port");
                password = properties.getProperty("password");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getHost() { return host; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public String getPort() { return port; }

}