package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.ClassDetailStudentViewModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StudentClassDetailApi {

    @GET("api/student-class-detail/{classId}")
    Single<ClassDetailStudentViewModel> getClassDetail(@Path("classId") int classId);

    @POST("api/student-class-detail/groups/{groupId}/join")
    Single<Void> joinGroup(@Path("groupId") int groupId); // Dùng <Void> vì API chỉ trả về message 200 OK

    @POST("api/student-class-detail/groups/{groupId}/leave")
    Single<Void> leaveGroup(@Path("groupId") int groupId);
}
