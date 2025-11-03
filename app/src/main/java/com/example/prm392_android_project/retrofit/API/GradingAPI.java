package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.GradingModel;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GradingAPI {
    @GET("api/Grading")
    Flowable<GradingModel> getGradingDetails(
            @Query("groupId") int groupId,
            @Query("assignmentId") int assignmentId
    );

    // 🔹 Lưu đánh giá nhóm
    @POST("api/Grading/save-group-grade")
    Completable saveGroupGrade(
            @Body GradingModel gradingModel
    );

    // 🔹 Lưu đánh giá từng thành viên
    @POST("api/Grading/save-member-grades")
    Completable saveMemberGrades(
            @Body GradingModel gradingModel
    );

}
