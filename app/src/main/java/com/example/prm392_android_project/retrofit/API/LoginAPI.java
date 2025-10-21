package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.LoginModel;
import com.example.prm392_android_project.models.truong.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginAPI {
    @POST("login")
    Call<LoginResponse> login(
            @Body LoginModel model
    );
}
