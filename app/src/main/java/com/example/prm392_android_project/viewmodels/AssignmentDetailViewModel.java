package com.example.prm392_android_project.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.prm392_android_project.dtos.AddGroupTaskRequest;
import com.example.prm392_android_project.models.GroupTask;
import com.example.prm392_android_project.retrofit.API.GroupTaskAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AssignmentDetailViewModel extends ViewModel {
    private final Retrofit retrofit = RetrofitClient.getInstance();
    private final GroupTaskAPI retrofitAPI = retrofit.create(GroupTaskAPI.class);
    private final CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<List<GroupTask>> groupTaskLiveData = new MutableLiveData<>();
    private final MutableLiveData<GroupTask> addingGroupTask = new MutableLiveData<>();

    public MutableLiveData<GroupTask> getAddingGroupTask() {
        return addingGroupTask;
    }
    public void initialSetGroupIdAndAssignmentIdToaddingGroupTask(int groupId, int assignmentId) {
        GroupTask groupTask = new GroupTask();
        groupTask.setGroupId(groupId);
        groupTask.setAssignmentId(assignmentId);
        groupTask.setPoints(0);
        groupTask.setTitle("");
        addingGroupTask.setValue(groupTask);
        fetchGroupTasks(assignmentId,groupId);
    }

    public AssignmentDetailViewModel() {
        mCompositeDisposable = new CompositeDisposable();

    }
    public LiveData<List<GroupTask>> getTodoGroupTaskLiveData() {
        return Transformations.map(groupTaskLiveData, input -> {
            if (input == null){
                return null;
            }
           return input.stream().filter(g -> g.getStatus().equals("todo")).collect(Collectors.toList());
        });
    }
    public LiveData<List<GroupTask>> getInProgressGroupTaskLiveData() {
        return Transformations.map(groupTaskLiveData, input -> {
            if (input == null) {
                return null;
            }
            return input.stream().filter(g -> g.getStatus().equals("doing")).collect(Collectors.toList());
        });
    }
    public LiveData<List<GroupTask>> getDoneGroupTaskLiveData() {
        return Transformations.map(groupTaskLiveData, input -> {
            if (input == null) {
                return null;
            }
            return input.stream().filter(g -> g.getStatus().equals("done")).collect(Collectors.toList());
        });
    }


    public void addGroupTask() {
        GroupTask groupTask = addingGroupTask.getValue();
        AddGroupTaskRequest addGroupTaskRequest = new AddGroupTaskRequest();
        if (groupTask != null) {
            addGroupTaskRequest.setGroupId(groupTask.getGroupId());
            addGroupTaskRequest.setAssignmentId(groupTask.getAssignmentId());
            addGroupTaskRequest.setTitle(groupTask.getTitle());
            addGroupTaskRequest.setPoints(groupTask.getPoints());
            addGroupTaskRequest.setAssignedTo(groupTask.getAssignedToId());


            Disposable disposable = retrofitAPI.addNewGroupTask(addGroupTaskRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                fetchGroupTasks(groupTask.getAssignmentId(), groupTask.getGroupId());
                                Log.d("addGroupTask", "Group task added successfully");
                            },
                            throwable -> {
                                Log.e("addGroupTask", "Error adding group task", throwable);
                            }
                    );
            mCompositeDisposable.add(disposable);
        }
    }
    private void fetchGroupTasks(int assignmentId, int groupId) {
        Disposable disposable = retrofitAPI.getGroupTasks(assignmentId,groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        groupTasks -> {
                            groupTaskLiveData.setValue(groupTasks);

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

    public void ChangeTaskStatus(int id){
        GroupTask groupTask = addingGroupTask.getValue();
        Disposable disposable = retrofitAPI.updateGroupTaskStatus(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onComplete
                        () -> {
                            assert groupTask != null;
                            fetchGroupTasks(groupTask.getAssignmentId(), groupTask.getGroupId());
                            Log.d("ChangeTaskStatus", "Task status updated successfully");
                        },
                        // onError
                        throwable -> {
                            Log.e("ChangeTaskStatus", "Error updating task status", throwable);
                        }
                );
        mCompositeDisposable.add(disposable);

    }

}

