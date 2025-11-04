package com.example.prm392_android_project.dtos;

public class JoinClassRequest {
    private String classCode;

    public JoinClassRequest(String classCode) {
        this.classCode = classCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
