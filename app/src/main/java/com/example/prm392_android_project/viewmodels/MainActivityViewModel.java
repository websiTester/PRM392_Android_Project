package com.example.prm392_android_project.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import com.example.prm392_android_project.retrofit.API.FCMTokenAPI;
import com.example.prm392_android_project.retrofit.API.GroupTaskAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "MainActivityViewModel";
    private final FirebaseDatabase mFirebaseDatabase;
    private final Retrofit retrofit = RetrofitClient.getInstance();
    private final FCMTokenAPI retrofitAPI = retrofit.create(FCMTokenAPI.class);
    private final CompositeDisposable mCompositeDisposable;
    private final SharedPreferences sharedPref ;
    private final  SharedPreferences studentSharedPref;
    private int groupId;

    public int getGroupId() {
        return groupId;
    }
    public void setClassId(int classId, CallBack callBack) {
        getGroupIdByClassId(classId, callBack);
    }


    public MainActivityViewModel(Application context) {
        super(context);
        mFirebaseDatabase =  FirebaseDatabase.getInstance("https://prm392finalproject-5c391-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mCompositeDisposable = new CompositeDisposable();
        sharedPref = context.getSharedPreferences("fcmToken", Context.MODE_PRIVATE);
        studentSharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        getClassesId();

    }
    private void addFCMToken(int classId){
        String key = "token" + classId;
        String spToken = sharedPref.getString(key, null);
        if(spToken != null) {
            return;
        }


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String saveToken = token + "-"+classId;
                    editor.putString(key, saveToken);
                    editor.apply();
                    Log.d(TAG, "Token: " + token);
                    String userId = "2";
                    DatabaseReference userTokensRef =mFirebaseDatabase
                            .getReference("fcmTokens")
                            .child(classId +"");
                    userTokensRef.child(userId).setValue(token);
                   });

    }
    private void getClassesId(){
        int studentId =studentSharedPref.getInt("userId",-1);
        Disposable disposable = retrofitAPI.getClassesByUserId(studentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    for(int i = 0; i < response.size(); i++) {
                        Log.d(TAG, "Class Id: " + response.get(i));
                        addFCMToken(response.get(i));
                    }

                }, throwable -> {
                    Log.e(TAG, "Error: " + throwable.getMessage());
                });

        mCompositeDisposable.add(disposable);
    }

    private void getGroupIdByClassId(int classId, CallBack callBack){
        int studentId =studentSharedPref.getInt("userId",-1);
        Disposable disposable = retrofitAPI.getGroupId(classId, studentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d(TAG, "Group Id: " + response);
                    callBack.getGroupIdSuccess(response);
                    groupId = response;
                }, throwable -> {
                    Log.e(TAG, "Error: " + throwable.getMessage());
                });

        mCompositeDisposable.add(disposable);
    }
    public interface CallBack{
        void getGroupIdSuccess(int groupId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
