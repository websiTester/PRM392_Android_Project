package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.models.GroupTask;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;

public interface GroupTaskAPI {
    @GET("/api/GroupTask")
    Flowable<List<GroupTask>> getGroupTasks();
}
