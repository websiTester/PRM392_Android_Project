package com.example.prm392_android_project.models;

import androidx.annotation.Nullable;

public class GroupTask {
    private int taskId;
    private String title;
    private String status;
    private int points;
    private String assignedToName;
    private int groupId;
    private int assignmentId;
    private int assignedToId;



    public GroupTask() {
    }

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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(int assignedToId) {
        this.assignedToId = assignedToId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GroupTask groupTask = (GroupTask) obj;
        return status.equals(groupTask.getStatus());
    }
}
