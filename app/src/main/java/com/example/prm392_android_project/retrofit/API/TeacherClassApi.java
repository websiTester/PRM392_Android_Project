package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.TeacherHomeResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TeacherClassApi {
    @GET("/api/TeacherClass/TeacherHome")
    Call<TeacherHomeResponse> getTeacherHome();

}
