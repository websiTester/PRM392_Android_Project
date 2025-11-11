package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.AddMemberToGroupRequest;
import com.example.prm392_android_project.dtos.CreateAssignmentRequest;
import com.example.prm392_android_project.dtos.CreateGroupRequest;
import com.example.prm392_android_project.models.AssignmentModel;
import com.example.prm392_android_project.models.ClassDetailTeacherViewModel;
import com.example.prm392_android_project.models.GroupModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TeacherClassDetailApi
{
    @GET("api/teacher-class-detail/{classId}")
    Single<ClassDetailTeacherViewModel> getClassDetail(@Path("classId") int classId);

    // --- Quản lý Assignment ---

    @POST("api/teacher-class-detail/{classId}/assignments")
    Single<AssignmentModel> createAssignment(
            @Path("classId") int classId,
            @Body CreateAssignmentRequest request
    );

    @PUT("api/teacher-class-detail/assignments/{assignmentId}")
    Single<Void> updateAssignment(
            @Path("assignmentId") int assignmentId,
            @Body CreateAssignmentRequest request
    );

    @DELETE("api/teacher-class-detail/assignments/{assignmentId}")
    Single<Void> deleteAssignment(@Path("assignmentId") int assignmentId);

    // --- Quản lý Group ---

    @POST("api/teacher-class-detail/{classId}/groups")
    Single<GroupModel> createGroup(
            @Path("classId") int classId,
            @Body CreateGroupRequest request
    );

    @DELETE("api/teacher-class-detail/groups/{groupId}")
    Single<Void> deleteGroup(@Path("groupId") int groupId);

    @POST("api/teacher-class-detail/groups/{groupId}/members")
    Single<Void> addStudentToGroup(
            @Path("groupId") int groupId,
            @Body AddMemberToGroupRequest request
    );

    @DELETE("api/teacher-class-detail/groups/{groupId}/members/{studentId}")
    Single<Void> removeStudentFromGroup(
            @Path("groupId") int groupId,
            @Path("studentId") int studentId
    );
}
