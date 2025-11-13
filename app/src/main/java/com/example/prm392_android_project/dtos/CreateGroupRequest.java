package com.example.prm392_android_project.dtos;

public class CreateGroupRequest {
    private String groupName;
    private int userId;

    public CreateGroupRequest(String groupName, int userId) {
        this.groupName = groupName;
        this.userId = userId;
    }
}
