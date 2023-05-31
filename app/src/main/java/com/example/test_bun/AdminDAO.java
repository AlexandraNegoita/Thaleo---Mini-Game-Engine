package com.example.test_bun;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminDAO {
    private static final String URL = "jdbc:mysql://192.168.30.135:3309/thaleodb";
    private static final String USER = "root";
    private static final String PASSWORD = "11111111";
    private static AdminDAO instance;
    private ArrayList<User> userData;
    private User user;

    private AdminDAO(){}

    public static synchronized AdminDAO getInstance(){
        if (instance == null) {
            instance = new AdminDAO();
        }
        return instance;
    }

    public User getUserData(int userID){
        return this.userData.get(userID);
    }

    public ArrayList<User> getUsers(){
        ResultSet resultSet = null;
        user = null;
        userData = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");

            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                user = new User(Integer.parseInt(resultSet.getString("userID")),
                        Integer.parseInt(resultSet.getString("roleID")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName")
                );
                userData.add(user);
            }
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error getting users information", e);
        }
        return userData;
    }

    public void changeUsername(int userID, String username){
        int resultSet = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET username='" + username + "' WHERE userID='" + userID + "'");

            resultSet = statement.executeUpdate();
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error updating user information", e);
        }
    }
    public void deleteUser(int userID){
        int resultSet = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users where userID='" + userID + "'");

            resultSet = statement.executeUpdate();
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error deleting user", e);
        }
    }
    public void addUser(String username, String password, String firstName, String lastName, String email){
        int resultSet = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                    "users(username, password, email, firstName, lastName, roleID) " +
                    "VALUES ('" + username + "', '" + password + "', '" + email + "', '" + firstName + "', '" + lastName+ "', 2)");

            resultSet = statement.executeUpdate();
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error adding user", e);
        }
    }


}
