package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.AddGroupTaskRequest;
import com.example.prm392_android_project.dtos.SubmitAssignmentRequest;
import com.example.prm392_android_project.models.AssignmentSubmission;
import com.example.prm392_android_project.models.GroupTask;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AssignmentSubmissionAPI {
    @GET("/api/AssignmentSubmission")
    Flowable<AssignmentSubmission> getAssignmentSubmission(@Query("assignmentId") int assignmentId, @Query("studentId") int studentId);
    @POST("/api/AssignmentSubmission/submit")
    Completable addNewAssignmentSubmission(
            @Body SubmitAssignmentRequest submitAssignmentRequest
            );
    @DELETE("/api/AssignmentSubmission")
    Completable deleteAssignmentSubmission(@Query("assignmentId") int assignmentId, @Query("studentId") int studentId);

}
