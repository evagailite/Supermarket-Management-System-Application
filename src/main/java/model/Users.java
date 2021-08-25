package model;

import types.UserType;

public class Users {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String password;
    private double budget;
    private UserType userType;

    public Users(Integer id, String username, String name,
                 String email, double budget, UserType userType) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.userType = userType;
    }

    public Users(String username, String name, String email, String password, double budget, UserType userType) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.budget = budget;
        this.userType = userType;
    }

    public Users(UserType userType, double budget) {
        this.userType = userType;
        this.budget = budget;
    }

    public Users() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getBudget() {
        return budget;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
