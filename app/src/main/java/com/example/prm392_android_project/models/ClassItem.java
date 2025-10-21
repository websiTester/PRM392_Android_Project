package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

public class ClassItem {


    @SerializedName("classId")
    private int classId;

    @SerializedName("className")
    private String className;

    @SerializedName("courseName")
    private String courseName;

    @SerializedName("teacherFullName")
    private String teacherFullName;
    @SerializedName("classCode")
    private String classCode;

    public ClassItem(int classId, String className, String courseName, String teacherFullName, String classCode) {
        this.classId = classId;
        this.className = className;
        this.courseName = courseName;
        this.teacherFullName = teacherFullName;
        this.classCode = classCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }
}
