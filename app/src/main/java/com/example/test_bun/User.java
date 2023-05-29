package com.example.test_bun;

public class User {
    int userID;
    int roleID;
    String role;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;

    public User(int userID, int roleID, String username, String password, String email, String firstName, String lastName) {
        this.userID = userID;
        this.roleID = roleID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public void setRole() {
        switch (roleID) {
            case 1: {
                this.role = "admin";
                break;
            }
            case 2: {
                this.role = "user";
                break;
            }
        }
    }

    public String getRole() {
        return this.role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
