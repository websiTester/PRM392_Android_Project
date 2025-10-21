package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherHomeResponse {
    @SerializedName("teacherId")
    private int teacherId;

    @SerializedName("classes")
    private List<ClassItem> classes;

    @SerializedName("allCourses")
    private List<CourseItem> allCourses;

    public TeacherHomeResponse(int teacherId, List<ClassItem> classes, List<CourseItem> allCourses) {
        this.teacherId = teacherId;
        this.classes = classes;
        this.allCourses = allCourses;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<ClassItem> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassItem> classes) {
        this.classes = classes;
    }

    public List<CourseItem> getAllCourses() {
        return allCourses;
    }

    public void setAllCourses(List<CourseItem> allCourses) {
        this.allCourses = allCourses;
    }
}
