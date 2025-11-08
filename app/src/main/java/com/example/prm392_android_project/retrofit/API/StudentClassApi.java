package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.CreateClassRequest;
import com.example.prm392_android_project.dtos.JoinClassRequest;
import com.example.prm392_android_project.models.StudentClassItem;
import com.example.prm392_android_project.models.TeacherHomeResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StudentClassApi {
    @GET("api/StudentClass/GetClasses")
    Call<List<StudentClassItem>> getStudentClasses();

    @POST("api/StudentClass/Join")
    Call<JsonObject> joinClass(@Body JoinClassRequest request);
}
