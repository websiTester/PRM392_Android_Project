package com.example.prm392_android_project.dtos;

public class CreateAssignmentRequest {
    private String title;
    private String description;
    private String deadline;
    private boolean isGroupAssignment;
    private int userId;
    public CreateAssignmentRequest(String title, String description, String deadline, boolean isGroupAssignment, int userId) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isGroupAssignment = isGroupAssignment;
        this.userId = userId;
    }
}
