package com.example.prm392_android_project.dtos;

public class CreateClassRequest {
    private String className;
    private int teacherId;
    private boolean isNewCourse;
    private Integer existingCourseId;
    private String newCourseName;
    private String newCourseDescription;

    public CreateClassRequest(String className, int teacherId, boolean isNewCourse, Integer existingCourseId, String newCourseName, String newCourseDescription) {
        this.className = className;
        this.teacherId = teacherId;
        this.isNewCourse = isNewCourse;
        this.existingCourseId = existingCourseId;
        this.newCourseName = newCourseName;
        this.newCourseDescription = newCourseDescription;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public boolean isNewCourse() {
        return isNewCourse;
    }

    public void setNewCourse(boolean newCourse) {
        isNewCourse = newCourse;
    }

    public Integer getExistingCourseId() {
        return existingCourseId;
    }

    public void setExistingCourseId(Integer existingCourseId) {
        this.existingCourseId = existingCourseId;
    }

    public String getNewCourseName() {
        return newCourseName;
    }

    public void setNewCourseName(String newCourseName) {
        this.newCourseName = newCourseName;
    }

    public String getNewCourseDescription() {
        return newCourseDescription;
    }

    public void setNewCourseDescription(String newCourseDescription) {
        this.newCourseDescription = newCourseDescription;
    }
}
