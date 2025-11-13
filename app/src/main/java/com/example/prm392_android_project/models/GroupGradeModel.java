package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

public class GroupGradeModel {
    @SerializedName("groupId")
    private int groupId;

    @SerializedName("grade")
    private Float grade;
    public int getGroupId() { return groupId; }
    public Float getGrade() { return grade; }
}
