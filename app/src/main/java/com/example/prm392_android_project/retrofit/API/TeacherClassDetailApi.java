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

public interface TeacherClassDetailApi
{
    @GET("api/teacher-class-detail/{classId}")
    Call<ClassDetailTeacherViewModel> getClassDetail(@Path("classId") int classId);

    // --- Quản lý Assignment ---

    @POST("api/teacher-class-detail/{classId}/assignments")
    Call<AssignmentModel> createAssignment( // <-- Sửa
                                            @Path("classId") int classId,
                                            @Body CreateAssignmentRequest request
    );

    @PUT("api/teacher-class-detail/assignments/{assignmentId}")
    Call<Void> updateAssignment( // <-- Sửa
                                 @Path("assignmentId") int assignmentId,
                                 @Body CreateAssignmentRequest request
    );

    @DELETE("api/teacher-class-detail/assignments/{assignmentId}")
    Call<Void> deleteAssignment(@Path("assignmentId") int assignmentId); // <-- Sửa

    // --- Quản lý Group ---

    @POST("api/teacher-class-detail/{classId}/groups")
    Call<GroupModel> createGroup( // <-- Sửa
                                  @Path("classId") int classId,
                                  @Body CreateGroupRequest request
    );

    @DELETE("api/teacher-class-detail/groups/{groupId}")
    Call<Void> deleteGroup(@Path("groupId") int groupId); // <-- Sửa

    @POST("api/teacher-class-detail/groups/{groupId}/members")
    Call<Void> addStudentToGroup( // <-- Sửa
                                  @Path("groupId") int groupId,
                                  @Body AddMemberToGroupRequest request
    );

    @DELETE("api/teacher-class-detail/groups/{groupId}/members/{studentId}")
    Call<Void> removeStudentFromGroup( // <-- Sửa
                                       @Path("groupId") int groupId,
                                       @Path("studentId") int studentId
    );
}
