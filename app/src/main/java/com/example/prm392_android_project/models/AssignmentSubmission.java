package com.example.prm392_android_project.models;

import androidx.annotation.NonNull;

import java.util.Date;

public class AssignmentSubmission {
    public int assignmentId;
    public String studentName ;
    public String submitLink ;
    public Date submittedAt;

    public AssignmentSubmission() {
    }

    public AssignmentSubmission(int assignmentId, String studentName, String submitLink, Date submittedAt) {
        this.assignmentId = assignmentId;
        this.studentName = studentName;
        this.submitLink = submitLink;
        this.submittedAt = submittedAt;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubmitLink() {
        return submitLink;
    }

    public void setSubmitLink(String submitLink) {
        this.submitLink = submitLink;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    @NonNull
    @Override
    public String toString() {
        return studentName + "has submitted assignment at " + submittedAt;
    }
}
