package com.example.prm392_android_project.models.truong;

import java.io.Serializable;

public class LoginResult implements Serializable {
    private int id;
    private String username;
    private String email;
    private String avatar;
    private String firstName;
    private String lastName;

    public LoginResult() {
    }

    public LoginResult(int id, String username, String email, String avarta, String firstname, String lastname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatar = avarta;
        this.firstName = firstname;
        this.lastName = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
