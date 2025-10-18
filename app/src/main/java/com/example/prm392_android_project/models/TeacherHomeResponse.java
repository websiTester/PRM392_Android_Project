package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherHomeResponse {
    @SerializedName("teacherId")
    private int teacherId;

    @SerializedName("classes")
    private List<ClassListDto> classes;

    @SerializedName("allCourses")
    private List<CourseDto> allCourses;

    public TeacherHomeResponse(int teacherId, List<ClassListDto> classes, List<CourseDto> allCourses) {
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

    public List<ClassListDto> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassListDto> classes) {
        this.classes = classes;
    }

    public List<CourseDto> getAllCourses() {
        return allCourses;
    }

    public void setAllCourses(List<CourseDto> allCourses) {
        this.allCourses = allCourses;
    }
}
