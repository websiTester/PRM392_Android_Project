package com.example.prm392_android_project.dtos;

import com.example.prm392_android_project.models.ClassItem;
import com.example.prm392_android_project.models.CourseItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentHomeResponse {
    @SerializedName("teacherId")
    private int teacherId;

    @SerializedName("classes")
    private List<ClassItem> classes;

    @SerializedName("allCourses")
    private List<CourseItem> allCourses;
}
