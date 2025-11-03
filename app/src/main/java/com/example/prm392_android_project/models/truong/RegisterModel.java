package com.example.prm392_android_project.models.truong;

public class RegisterModel {
    public String username="";
    public String password ="";
    public String confirmPassword="" ;
    public int roleId ;

    public RegisterModel() {
    }

    public RegisterModel(String username, String password, String confirmPassword, int roleId) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roleId = roleId;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
