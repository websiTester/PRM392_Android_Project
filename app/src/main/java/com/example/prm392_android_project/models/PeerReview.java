package com.example.prm392_android_project.models;

public class PeerReview {
    public int groupId;
    public int assignmentId;
    public int reviewerId;
    public int revieweeId;
    public String comment;
    public float score;

    public PeerReview(int groupId, int assignmentId, int reviewerId, int revieweeId, String comment, float score) {
        this.groupId = groupId;
        this.assignmentId = assignmentId;
        this.reviewerId = reviewerId;
        this.revieweeId = revieweeId;
        this.comment = comment;
        this.score = score;
    }

    public PeerReview() {
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
