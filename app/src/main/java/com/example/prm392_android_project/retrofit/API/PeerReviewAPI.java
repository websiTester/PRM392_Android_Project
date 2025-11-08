package com.example.prm392_android_project.retrofit.API;

import com.example.prm392_android_project.dtos.AddGroupTaskRequest;
import com.example.prm392_android_project.dtos.ModifyPeerReview;
import com.example.prm392_android_project.models.GroupTask;
import com.example.prm392_android_project.models.PeerReview;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PeerReviewAPI {
    @GET("/api/PeerReview/get-peer-review")
    Flowable<PeerReview> getPeerReview(@Query("reviewerId") int reviewerId, @Query("revieweeId") int revieweeId,
                                             @Query("assignmentId") int assignmentId, @Query("groupId") int groupId);

    @PUT("/api/PeerReview/update-peer-review")
    Completable updatePeerReview(
            @Body ModifyPeerReview request
            );
    @POST("/api/PeerReview/add-peer-review")
    Completable addPeerReview(
            @Body ModifyPeerReview request
    );
}
