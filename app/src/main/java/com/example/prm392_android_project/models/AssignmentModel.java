package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignmentModel {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("deadline")
    private String deadline;
    @SerializedName("isGroupAssignment")
    private boolean isGroupAssignment;
    @SerializedName("studentGradeDisplay")
    private String studentGradeDisplay;

    public List<GroupGradeModel> getGroupGrades() {
        return groupGrades;
    }

    public void setGroupGrades(List<GroupGradeModel> groupGrades) {
        this.groupGrades = groupGrades;
    }

    public String getStudentGradeDisplay() {
        return studentGradeDisplay;
    }

    public void setStudentGradeDisplay(String studentGradeDisplay) {
        this.studentGradeDisplay = studentGradeDisplay;
    }

    @SerializedName("groupGrades")
    private List<GroupGradeModel> groupGrades;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isGroupAssignment() {
        return isGroupAssignment;
    }

    public void setGroupAssignment(boolean groupAssignment) {
        isGroupAssignment = groupAssignment;
    }
}
