package com.example.prm392_android_project.dtos;

public class ModifyPeerReview {
    public int groupId;
    public int assignmentId;
    public int reviewerId;
    public int revieweeId;
    public String comment;
    public double score;

    public ModifyPeerReview() {
    }

    public ModifyPeerReview(int groupId, int assignmentId, int reviewerId, int revieweeId, String comment, double score) {
        this.groupId = groupId;
        this.assignmentId = assignmentId;
        this.reviewerId = reviewerId;
        this.revieweeId = revieweeId;
        this.comment = comment;
        this.score = score;
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

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public int getRevieweeId() {
        return revieweeId;
    }

    public void setRevieweeId(int revieweeId) {
        this.revieweeId = revieweeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
