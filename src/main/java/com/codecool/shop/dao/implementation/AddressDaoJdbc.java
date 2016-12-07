package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AddressDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.customer.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by prezi on 2016. 12. 07..
 */
public class AddressDaoJdbc implements AddressDao {
    private static Connection databaseConnection;
    private static AddressDaoJdbc INSTANCE;
    private static String DB_PROPERTIES_PATH = "src/main/resources/properties/db_config.properties";

    public static AddressDaoJdbc getInstance(DatabaseConnection dbConnection) {

        if (INSTANCE == null) {
            INSTANCE = new AddressDaoJdbc();
            try {
                databaseConnection = dbConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }


    public static AddressDaoJdbc getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new AddressDaoJdbc();
            try {
                databaseConnection = DatabaseConnection.getInstance(DB_PROPERTIES_PATH).getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

    @Override
    public void saveAddress(Address address){
        String sql = "INSERT INTO products (country, city, zipcode, address) ";
        String values = "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement prepStmnt = databaseConnection.prepareStatement(sql + values);
            prepStmnt.setString(1, address.getCountry());
            prepStmnt.setString(1, address.getCity());
            prepStmnt.setString(1, address.getZipcode());
            prepStmnt.setString(1, address.getAddress());
            prepStmnt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int findAddressID(Address address){
        return 0;
    }
}
