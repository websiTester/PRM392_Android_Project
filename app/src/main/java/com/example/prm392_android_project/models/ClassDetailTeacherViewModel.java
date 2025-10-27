package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassDetailTeacherViewModel {
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

    @SerializedName("allStudents")
    private List<ClassMemberModel> allStudents;

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

    public List<ClassMemberModel> getAllStudents() {
        return allStudents;
    }

    public void setAllStudents(List<ClassMemberModel> allStudents) {
        this.allStudents = allStudents;
    }
}
