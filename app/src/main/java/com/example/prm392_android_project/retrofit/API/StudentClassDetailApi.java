package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.ClassDetailStudentViewModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StudentClassDetailApi {

    @GET("api/student-class-detail/{classId}")
    Call<ClassDetailStudentViewModel> getClassDetail(@Path("classId") int classId);

    @POST("api/student-class-detail/groups/{groupId}/join")
    Call<Void> joinGroup(@Path("groupId") int groupId);

    @POST("api/student-class-detail/groups/{groupId}/leave")
    Call<Void> leaveGroup(@Path("groupId") int groupId);
}
