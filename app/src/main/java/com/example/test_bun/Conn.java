package com.example.test_bun;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Conn {
    private static final String URL = "jdbc:mysql://192.168.1.124:3306/thaleodb";
    private static final String USER = "test";
    private static final String PASSWORD = "123";
    private static Conn instance;
    private Conn(){}
    
    public static synchronized Conn getInstance(){
        if (instance == null) {
            instance = new Conn();
        }
        return instance;
    }
    
    public ResultSet execute(String query){
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }
        return resultSet;
    }
}
