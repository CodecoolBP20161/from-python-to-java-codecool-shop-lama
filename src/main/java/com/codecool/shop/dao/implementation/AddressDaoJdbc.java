package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AddressDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.customer.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public int saveAddress(Address address){
        String sql = "INSERT INTO address (country, city, zip_code, address) ";
        String values = "VALUES (?, ?, ?, ?) returning id";
        try {
            PreparedStatement prepStmnt = databaseConnection.prepareStatement(sql + values);
            prepStmnt.setString(1, address.getCountry());
            prepStmnt.setString(2, address.getCity());
            prepStmnt.setString(3, address.getZipcode());
            prepStmnt.setString(4, address.getAddress());
            prepStmnt.execute();
            ResultSet lastAddedAddress = prepStmnt.getResultSet();
            while (lastAddedAddress.next()){
                return lastAddedAddress.getInt("id");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    private int findAddressID(int id){
//        try {
//            PreparedStatement preparedStatement = databaseConnection
//                    .prepareStatement("SELECT * FROM address WHERE id = ?;");
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                Address address = new Address(resultSet.getString("name")
////                return customer;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
