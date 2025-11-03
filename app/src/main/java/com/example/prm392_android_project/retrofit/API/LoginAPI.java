package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.LoginModel;
import com.example.prm392_android_project.models.truong.LoginResult;
import com.example.prm392_android_project.models.truong.RegisterModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface LoginAPI {
    @POST("/api/Account/login")
    Call<LoginResult> login(
            @Body LoginModel model
    );

    @PUT("/api/Account/Update")
    Call<LoginResult> updateUser(
            @Body LoginResult model
    );

    @POST("/api/Account/Register")
    Call<ResponseBody> registerUser(
            @Body RegisterModel model
    );
}
