package com.example.prm392_android_project.models;

public class UserItem {
    private int userId;
    private String fullName;
    private String email;
    private String roleName;
    private boolean isActive;
    private String avatar;

    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRoleName() { return roleName; }
    public boolean isActive() { return isActive; }
    public String getAvatar() { return avatar; }
    public void setUserId(int id){
        userId =id;
    }
    public void setFullName(String fullname){
        fullName = fullname;
    }
    public void setRoleName(String rolename){
        roleName = rolename;
    }
    public void setEmail(String mail){
        email = mail;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
}
