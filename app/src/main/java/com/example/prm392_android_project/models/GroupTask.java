package com.example.prm392_android_project.models;

public class GroupTask {
    private int taskId;
    private String title;
    private String status;
    private int points;
    private String assignedToName;

    public GroupTask(int taskId, String title, String status, int points, String assignedToName) {
        this.taskId = taskId;
        this.title = title;
        this.status = status;
        this.points = points;
        this.assignedToName = assignedToName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
