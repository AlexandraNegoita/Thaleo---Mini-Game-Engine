package com.example.test_bun;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private static final String URL = "jdbc:mysql://192.168.1.124:3309/thaleodb";
    private static final String USER = "root";
    private static final String PASSWORD = "11111111";
    private static UserDAO instance;
    private User userData;

    private UserDAO(){}

    public static synchronized UserDAO getInstance(){
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public User getUserData(){
        return this.userData;
    }
    public User login(String usernameText, String passwordText){
        ResultSet resultSet = null;
        userData = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username='" + usernameText + "' AND password='" + passwordText+"'");

            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                userData = new User(Integer.parseInt(resultSet.getString("userID")),
                        Integer.parseInt(resultSet.getString("roleID")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName")
                );
            }
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }
        return userData;
    }

    public void register(String username, String password, String firstName, String lastName, String email){
        int resultSet = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                    "users(username, password, email, firstName, lastName, roleID) " +
                    "VALUES ('" + username + "', '" + password + "', '" + email + "', '" + firstName + "', '" + lastName+ "', 2)");

            resultSet = statement.executeUpdate();
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }
    }


}
