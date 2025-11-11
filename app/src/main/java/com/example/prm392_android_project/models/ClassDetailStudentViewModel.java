package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassDetailStudentViewModel {
    @SerializedName("classId")
    private int classId;
    @SerializedName("className")
    private String className;
    @SerializedName("classCode")
    private String classCode;
    @SerializedName("teacher")
    private ClassMemberModel teacher;
    @SerializedName("assignments")
    private List<AssignmentModel> assignments;
    @SerializedName("groups")
    private List<GroupModel> groups;
    @SerializedName("currentUserGroupId")
    private Integer currentUserGroupId;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public ClassMemberModel getTeacher() {
        return teacher;
    }

    public void setTeacher(ClassMemberModel teacher) {
        this.teacher = teacher;
    }

    public List<AssignmentModel> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentModel> assignments) {
        this.assignments = assignments;
    }

    public List<GroupModel> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupModel> groups) {
        this.groups = groups;
    }

    public Integer getCurrentUserGroupId() {
        return currentUserGroupId;
    }

    public void setCurrentUserGroupId(Integer currentUserGroupId) {
        this.currentUserGroupId = currentUserGroupId;
    }
}
