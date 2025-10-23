package com.example.prm392_android_project.dtos;

import androidx.annotation.NonNull;

public class GetUserForDropdownResponse {
    private int userId;
    public String firstName;
    private String lastName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    @Override
    public String toString() {
        return firstName +" "+ lastName ;
    }
}
