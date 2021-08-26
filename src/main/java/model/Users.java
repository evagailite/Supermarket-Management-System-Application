package model;

import types.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
