package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

public class TaskModel {
    @SerializedName("title")
    private String title;

    @SerializedName("status")
    private String status; // "To Do", "Doing", "Done"

    @SerializedName("points")
    private Integer points; // tương ứng với int? trong C#

    // --- Constructor rỗng (bắt buộc cho Gson/Retrofit) ---
    public TaskModel() {}

    // --- Constructor đầy đủ ---
    public TaskModel(String title, String status, Integer points) {
        this.title = title;
        this.status = status;
        this.points = points;
    }

    // --- Getter & Setter ---
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    // --- (Tuỳ chọn) toString() để dễ debug ---
    @Override
    public String toString() {
        return "TaskModel{" +
                "title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", points=" + points +
                '}';
    }
}
