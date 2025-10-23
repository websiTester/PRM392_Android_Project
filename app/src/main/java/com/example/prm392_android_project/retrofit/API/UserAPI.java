package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.GetUserForDropdownResponse;
import com.example.prm392_android_project.models.GroupTask;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("/api/User/GetUserInGroup")
    Single<List<GetUserForDropdownResponse>> getUsersForDropdown(@Query("groupId") int groupId);
}
