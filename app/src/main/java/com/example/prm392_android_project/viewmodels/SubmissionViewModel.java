package com.example.prm392_android_project.viewmodels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prm392_android_project.dtos.SubmitAssignmentRequest;
import com.example.prm392_android_project.models.AssignmentSubmission;
import com.example.prm392_android_project.models.GroupTask;
import com.example.prm392_android_project.retrofit.API.AssignmentSubmissionAPI;
import com.example.prm392_android_project.retrofit.API.GroupTaskAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class SubmissionViewModel extends AndroidViewModel {
    private static final String TAG = "SubmissionViewModel";
    private final Retrofit retrofit = RetrofitClient.getInstance();
    private final AssignmentSubmissionAPI retrofitAPI = retrofit.create(AssignmentSubmissionAPI.class);
    private final CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<AssignmentSubmission> assignmentSubmission = new MutableLiveData<>();
    public SubmissionViewModel(Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
    }
    public void initialSetGroupIdAndAssignmentId(int assignmentId) {
        //thay bằng id của người dùng
        Disposable disposable = retrofitAPI.getAssignmentSubmission(assignmentId, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        as -> {
                            assignmentSubmission.setValue(as);
                            Log.d(TAG, "Fetched submission successfully.");
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException httpEx = (HttpException) throwable;
                                if (httpEx.code() == 404) {
                                    AssignmentSubmission submission = new AssignmentSubmission();
                                    submission.setAssignmentId(assignmentId);
                                    assignmentSubmission.setValue(submission);
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
    public LiveData<AssignmentSubmission> getAssignmentSubmission(){
        return assignmentSubmission;
    }
    public void addAssignmentSubmission(int studentId) {
        AssignmentSubmission submission = assignmentSubmission.getValue();
        SubmitAssignmentRequest submitAssignmentRequest = new SubmitAssignmentRequest();
         submitAssignmentRequest.setStudentId(studentId);
            submitAssignmentRequest.setAssignmentId(submission.getAssignmentId());
            submitAssignmentRequest.setSubmitLink(submission.getSubmitLink());
        Disposable disposable = retrofitAPI.addNewAssignmentSubmission(submitAssignmentRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.d(TAG, "Submission added successfully");
                            Toast.makeText(getApplication(), "Submission added successfully", Toast.LENGTH_SHORT).show();
                        },
                        throwable -> {
                            Log.e(TAG, "Error Submission", throwable);
                            Toast.makeText(getApplication(), "Error Submission", Toast.LENGTH_SHORT).show();
                        }
                );
        mCompositeDisposable.add(disposable);
    }

    public void cancelAssignmentSubmission(int studentId){
        AssignmentSubmission submission = assignmentSubmission.getValue();
        assert submission != null;
        int assignmentId = submission.getAssignmentId();
        Disposable disposable = retrofitAPI.deleteAssignmentSubmission(assignmentId,studentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.d(TAG, "Submission deleted successfully");
                            Toast.makeText(getApplication(), "Submission deleted successfully", Toast.LENGTH_SHORT).show();
                        },
                        throwable -> {
                            Log.e(TAG, "Error Deleting Submission", throwable);
                            Toast.makeText(getApplication(), "Error Deleting Submission", Toast.LENGTH_SHORT).show();
                        }
                );
        mCompositeDisposable.add(disposable);


    }
    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
