package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GradingModel {
    @SerializedName("assignmentId")
    private int assignmentId;

    @SerializedName("assignmentName")
    private String assignmentName;

    @SerializedName("classId")
    private int classId;

    @SerializedName("groupId")
    private int groupId;

    @SerializedName("groupName")
    private String groupName;

    @SerializedName("submissionLink")
    private String submissionLink;

    @SerializedName("groupGrade")
    private Double groupGrade; // dùng Double để cho phép null

    @SerializedName("groupComment")
    private String groupComment;

    @SerializedName("members")
    private List<MemberGradingModel> members;

    // --- Constructor rỗng: BẮT BUỘC cho Gson/Retrofit ---
    public GradingModel() {
        this.members = new ArrayList<>();
    }

    // --- Constructor đầy đủ ---
    public GradingModel(int assignmentId,
                        String assignmentName,
                        int classId,
                        int groupId,
                        String groupName,
                        String submissionLink,
                        Double groupGrade,
                        String groupComment,
                        List<MemberGradingModel> members) {
        this.assignmentId = assignmentId;
        this.assignmentName = assignmentName;
        this.classId = classId;
        this.groupId = groupId;
        this.groupName = groupName;
        this.submissionLink = submissionLink;
        this.groupGrade = groupGrade;
        this.groupComment = groupComment;
        this.members = (members != null) ? members : new ArrayList<>();
    }

    // --- (Tuỳ chọn) constructor tiện lợi khi chỉ có id ---
    public GradingModel(int assignmentId, int groupId) {
        this();
        this.assignmentId = assignmentId;
        this.groupId = groupId;
    }

    // --- Getter/Setter ---
    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public String getAssignmentName() { return assignmentName; }
    public void setAssignmentName(String assignmentName) { this.assignmentName = assignmentName; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public int getGroupId() { return groupId; }
    public void setGroupId(int groupId) { this.groupId = groupId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getSubmissionLink() { return submissionLink; }
    public void setSubmissionLink(String submissionLink) { this.submissionLink = submissionLink; }

    public Double getGroupGrade() { return groupGrade; }
    public void setGroupGrade(Double groupGrade) { this.groupGrade = groupGrade; }

    public String getGroupComment() { return groupComment; }
    public void setGroupComment(String groupComment) { this.groupComment = groupComment; }

    public List<MemberGradingModel> getMembers() { return members; }
    public void setMembers(List<MemberGradingModel> members) {
        this.members = (members != null) ? members : new ArrayList<>();
    }

}
