package com.example.prm392_android_project.dtos;

public class JoinClassRequest {

    private int studentId;
    private String classCode;

    public JoinClassRequest(int studentId, String classCode) {
        this.studentId = studentId;
        this.classCode = classCode;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
