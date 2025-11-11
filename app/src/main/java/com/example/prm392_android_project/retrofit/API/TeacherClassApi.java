package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.CreateClassRequest;
import com.example.prm392_android_project.models.TeacherHomeResponse;
import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TeacherClassApi {
    @GET("/api/TeacherClass/TeacherHome")
    Call<TeacherHomeResponse> getTeacherHome(@Query("teacherId")int teacherId);


    @POST("/Class/Create")
    Call<JsonObject> createClass(@Body CreateClassRequest request);

}
