package com.example.prm392_android_project.viewmodels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prm392_android_project.dtos.ModifyPeerReview;
import com.example.prm392_android_project.models.AssignmentSubmission;
import com.example.prm392_android_project.models.PeerReview;
import com.example.prm392_android_project.retrofit.API.PeerReviewAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class PeerReviewViewModel extends AndroidViewModel {
    private static final String TAG = "PeerReviewViewModel";
    private final Retrofit retrofit = RetrofitClient.getInstance();
    private final PeerReviewAPI retrofitAPI = retrofit.create(PeerReviewAPI.class);
    private final CompositeDisposable mCompositeDisposable;
    private boolean isReviewExisted;
    private MutableLiveData<PeerReview> mPeerReview = new MutableLiveData<>();
    public LiveData<PeerReview> getPeerReview() {
        return mPeerReview;
    }
    public PeerReviewViewModel(Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
    }
    public void fetchPeerReview(int reviewerId, int revieweeId, int assignmentId, int groupId){
        Disposable disposable = retrofitAPI.getPeerReview(reviewerId,revieweeId,assignmentId,groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pr -> {
                            mPeerReview.setValue(pr);
                            isReviewExisted = true;
                            Log.d(TAG, "Fetched review successfully.");
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException httpEx = (HttpException) throwable;
                                if (httpEx.code() == 404) {
                                    PeerReview peerReview = new PeerReview();
                                    isReviewExisted = false;
                                    mPeerReview.setValue(peerReview);
                                    Log.w(TAG, "Submission not found (404). Created empty submission.");
                                } else {
                                    Log.e(TAG, "HTTP error: " + httpEx.code());
                                }
                            } else {
                                Log.e(TAG, "Error fetching submission: " + throwable.getMessage());
                            }
                        }
                );
        mCompositeDisposable.add(disposable);
    }

    public void addPeerReview(int reviewerId, int revieweeId, int assignmentId, int groupId) {
        PeerReview peerReview = mPeerReview.getValue();
        ModifyPeerReview modifyPeerReview = new ModifyPeerReview();
        if (peerReview != null) {
            modifyPeerReview.setReviewerId(reviewerId);
            modifyPeerReview.setRevieweeId(revieweeId);
            modifyPeerReview.setAssignmentId(assignmentId);
            modifyPeerReview.setGroupId(groupId);
            modifyPeerReview.setComment(peerReview.getComment());
            modifyPeerReview.setScore(peerReview.getScore());
        }

        if(!isReviewExisted){
            Disposable disposable = retrofitAPI.addPeerReview(modifyPeerReview)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                Log.d(TAG, "Review added successfully");
                                Toast.makeText(getApplication(), "Review added successfully", Toast.LENGTH_SHORT).show();
                            },
                            throwable -> {
                                Log.e(TAG, "Error Adding Review", throwable);
                                Toast.makeText(getApplication(), "Error Adding Review", Toast.LENGTH_SHORT).show();
                            }
                    );
            mCompositeDisposable.add(disposable);
        }else{
            Disposable disposable = retrofitAPI.updatePeerReview(modifyPeerReview)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                Log.d(TAG, "Review updated successfully");
                                Toast.makeText(getApplication(), "Review updated successfully", Toast.LENGTH_SHORT).show();
                            },
                            throwable -> {
                                Log.e(TAG, "Error Updating Review", throwable);
                                Toast.makeText(getApplication(), "Error Updating Review", Toast.LENGTH_SHORT).show();
                            }
                    );
            mCompositeDisposable.add(disposable);
        }
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
