package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.AddGroupTaskRequest;
import com.example.prm392_android_project.models.GroupTask;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GroupTaskAPI {
    @GET("/api/GroupTask")
    Flowable<List<GroupTask>> getGroupTasks(@Query("assignmentId") int assignmentId, @Query("groupId") int groupId);
    @PUT("/api/GroupTask/UpdateGroupTaskStatus")
    Completable updateGroupTaskStatus(
            @Query("id") int id
    );
    @POST("/api/GroupTask")
    Completable addNewGroupTask(
         @Body AddGroupTaskRequest groupTask
    );

}
