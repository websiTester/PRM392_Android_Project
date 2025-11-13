package com.example.prm392_android_project.dtos;

public class AddMemberToGroupRequest {
    private int studentId;
    private int userId;
    public AddMemberToGroupRequest(int studentId, int userId) {
        this.studentId = studentId;
        this.userId = userId;
    }
}
