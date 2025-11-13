package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.AddMemberToGroupRequest;
import com.example.prm392_android_project.dtos.CreateAssignmentRequest;
import com.example.prm392_android_project.dtos.CreateGroupRequest;
import com.example.prm392_android_project.models.AssignmentModel;
import com.example.prm392_android_project.models.ClassDetailTeacherViewModel;
import com.example.prm392_android_project.models.GroupModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TeacherClassDetailApi
{
    @GET("api/teacher-class-detail/{classId}")
    Call<ClassDetailTeacherViewModel> getClassDetail(
            @Path("classId") int classId,
            @Query("userId") int userId
    );

    @POST("api/teacher-class-detail/{classId}/assignments")
    Call<AssignmentModel> createAssignment(
            @Path("classId") int classId,
            @Body CreateAssignmentRequest request
    );

    @PUT("api/teacher-class-detail/assignments/{assignmentId}")
    Call<Void> updateAssignment(
            @Path("assignmentId") int assignmentId,
            @Body CreateAssignmentRequest request
    );

    @DELETE("api/teacher-class-detail/assignments/{assignmentId}")
    Call<Void> deleteAssignment(
            @Path("assignmentId") int assignmentId,
            @Query("userId") int userId
    );

    @POST("api/teacher-class-detail/{classId}/groups")
    Call<GroupModel> createGroup(
            @Path("classId") int classId,
            @Body CreateGroupRequest request
    );

    @DELETE("api/teacher-class-detail/groups/{groupId}")
    Call<Void> deleteGroup(
            @Path("groupId") int groupId,
            @Query("userId") int userId
    );

    @POST("api/teacher-class-detail/groups/{groupId}/members")
    Call<Void> addStudentToGroup(
            @Path("groupId") int groupId,
            @Body AddMemberToGroupRequest request
    );

    @DELETE("api/teacher-class-detail/groups/{groupId}/members/{studentId}")
    Call<Void> removeStudentFromGroup(
            @Path("groupId") int groupId,
            @Path("studentId") int studentId,
            @Query("userId") int userId
    );
}
