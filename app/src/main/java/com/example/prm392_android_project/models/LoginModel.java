package com.example.prm392_android_project.models;

public class LoginModel {
    private String username="";
    private String password="";

    public LoginModel(){

    }

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassowrd() {
        return password;
    }

    public void setPassowrd(String password) {
        this.password = password;
    }
}
