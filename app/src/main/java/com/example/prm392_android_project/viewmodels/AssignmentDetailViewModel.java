package com.example.prm392_android_project.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prm392_android_project.models.GroupTask;
import com.example.prm392_android_project.retrofit.API.GroupTaskAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AssignmentDetailViewModel extends AndroidViewModel {
    private final Retrofit retrofit = RetrofitClient.getInstance();
    private final GroupTaskAPI retrofitAPI = retrofit.create(GroupTaskAPI.class);
    private final CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<List<GroupTask>> articleLiveData = new MutableLiveData<>();
    public AssignmentDetailViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
        fetchGroupTasks();
    }
    public LiveData<List<GroupTask>> getArticleLiveData() {
        return articleLiveData;
    }
    public void fetchGroupTasks() {
        Disposable disposable = retrofitAPI.getGroupTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        groupTasks -> {
                            articleLiveData.setValue(groupTasks);

                            if (groupTasks != null) {
                                Log.d("API_CALL", "Fetched " + groupTasks.size() + " articles successfully.");
                            } else {
                                Log.d("API_CALL", "Fetched 0 articles or list is null.");
                            }
                        },
                        throwable -> {
                            Log.e("API_CALL", "Error fetching articles: " + throwable.getMessage());
                        }
                );
        mCompositeDisposable.add(disposable);
    }

}

