package com.example.prm392_android_project.models;

public class LoginModel {
    private String username;
    private String passowrd;

    public LoginModel(){

    }

    public LoginModel(String username, String passowrd) {
        this.username = username;
        this.passowrd = passowrd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }
}
