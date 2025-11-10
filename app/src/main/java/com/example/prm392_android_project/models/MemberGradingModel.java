package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MemberGradingModel {
    @SerializedName("studentId")
    private int studentId;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("isLeader")
    private boolean isLeader;

    @SerializedName("grade")
    private Double grade; // nullable giống kiểu decimal? trong C#

    @SerializedName("comment")
    private String comment;

    @SerializedName("toDoCount")
    private int toDoCount;

    @SerializedName("doingCount")
    private int doingCount;

    @SerializedName("doneCount")
    private int doneCount;

    @SerializedName("tasks")
    private List<TaskModel> tasks;

    // --- Constructor rỗng: BẮT BUỘC cho Gson/Retrofit ---
    public MemberGradingModel() {
    }

    // --- Constructor đầy đủ ---
    public MemberGradingModel(int studentId,
                                  String fullName,
                                  boolean isLeader,
                                  Double grade,
                                  String comment,
                                  int toDoCount,
                                  int doingCount,
                                  int doneCount,
                                  List<TaskModel> tasks) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.isLeader = isLeader;
        this.grade = grade;
        this.comment = comment;
        this.toDoCount = toDoCount;
        this.doingCount = doingCount;
        this.doneCount = doneCount;
        this.tasks = (tasks != null) ? tasks : new ArrayList<>();
    }

    // --- Getter & Setter ---
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public boolean isLeader() { return isLeader; }
    public void setLeader(boolean leader) { isLeader = leader; }

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getToDoCount() { return toDoCount; }
    public void setToDoCount(int toDoCount) { this.toDoCount = toDoCount; }

    public int getDoingCount() { return doingCount; }
    public void setDoingCount(int doingCount) { this.doingCount = doingCount; }

    public int getDoneCount() { return doneCount; }
    public void setDoneCount(int doneCount) { this.doneCount = doneCount; }

    public List<TaskModel> getTasks() { return tasks; }
    public void setTasks(List<TaskModel> tasks) {
        this.tasks = (tasks != null) ? tasks : new ArrayList<>();
    }
}
