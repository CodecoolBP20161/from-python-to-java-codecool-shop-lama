package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.Supplier;

import com.sun.tools.javac.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by prezi on 2016. 11. 22..
 */
public class SupplierDaoJdbc implements SupplierDao {

    private static SupplierDaoJdbc instance = null;
    private static List<Supplier> DATA = new ArrayList<>();
    private DatabaseConnection dbc = DatabaseConnection.getInstance();
    private Connection conn = null;
    private PreparedStatement statement = null;
    private String sql = null;

    private SupplierDaoJdbc(){

    }

    public static SupplierDaoJdbc getInstance(){
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier){

        sql = "INSERT INTO suppliers "
                        + "(name, description) VALUES"
                        + "(?,?)";

        try {
            conn = dbc.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(2, supplier.getName());
            statement.setString(3, supplier.getDescription());

            statement.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        }

    @Override
    public Supplier find(int id){
        sql = "SELECT * FROM suppliers WHERE id = ?";

        try {
            conn = dbc.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet queryResult = statement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void remove(int id){

    }

    @Override
    public java.util.List<Supplier> getAll() {
        return DATA;
    }

}
