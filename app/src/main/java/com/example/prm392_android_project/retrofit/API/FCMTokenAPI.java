package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.AddFCMTokenRequest;
import com.example.prm392_android_project.dtos.GetUserForDropdownResponse;
import com.example.prm392_android_project.models.GradingModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FCMTokenAPI {
    @POST("api/FCMToken/save-token")
    Completable saveFCMToken(
            @Body List<String> tokens
    );
    @GET("/api/FCM/get-classes")
    Single<List<Integer>> getClassesByUserId(@Query("userId") int userId);
    @GET("/api/FCM/get-groupId")
    Single<Integer> getGroupId(@Query("classId") int classId, @Query("userId") int userId);

}
