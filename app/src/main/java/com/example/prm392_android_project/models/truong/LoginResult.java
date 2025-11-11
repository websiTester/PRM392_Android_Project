package com.example.prm392_android_project.models.truong;

import java.io.Serializable;

public class LoginResult implements Serializable {
    private int userId;
    private String username;
    private String email;
    private String avatar;
    private String firstName;
    private String lastName;

    private  int roleId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public LoginResult() {
    }

    public LoginResult(int userId, String username, String email, String avatar, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LoginResult(int userId, String username, String email, String avatar, String firstName, String lastName, int roleId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvarta() {
        return avatar;
    }

    public void setAvarta(String avarta) {
        this.avatar = avarta;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }
}
