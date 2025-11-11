package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.DashboardModel;
import com.example.prm392_android_project.models.UserItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DashboardAPI {
    @GET("api/Dashboard/dashboard")
    Call<DashboardModel> getDashboard();

    // GET /api/Dashboard/user
    @GET("api/Dashboard/user")
    Call<List<UserItem>> getUserList();

    // GET /api/Dashboard/user/{id}
    @GET("api/Dashboard/user/{id}")
    Call<UserItem> getUserById(@Path("id") int id);

    // PUT /api/Dashboard/user/{id}/status?status=true|false
    @PUT("api/Dashboard/user/{id}/status")
    Call<Void> updateUserStatus(
            @Path("id") int id,
            @Query("status") boolean status);
}
