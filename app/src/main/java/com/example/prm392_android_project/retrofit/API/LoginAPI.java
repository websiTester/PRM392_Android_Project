package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.LoginModel;
import com.example.prm392_android_project.models.truong.LoginResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginAPI {
    @POST("/api/Account/login")
    Call<LoginResult> login(
            @Body LoginModel model
    );
}
