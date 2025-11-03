package com.example.prm392_android_project.dtos;

import java.util.Date;

public class SubmitAssignmentRequest {
    public int assignmentId;
    public int studentId ;
    public String submitLink ;

    public SubmitAssignmentRequest() {
    }

    public SubmitAssignmentRequest(int assignmentId, int studentId, String submitLink) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submitLink = submitLink;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubmitLink() {
        return submitLink;
    }

    public void setSubmitLink(String submitLink) {
        this.submitLink = submitLink;
    }

}
