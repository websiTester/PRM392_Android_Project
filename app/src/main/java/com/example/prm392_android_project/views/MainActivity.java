package com.example.prm392_android_project.views;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.dtos.AddFCMTokenRequest;
import com.example.prm392_android_project.retrofit.API.FCMTokenAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;
import com.example.prm392_android_project.viewmodels.AssignmentDetailViewModel;
import com.example.prm392_android_project.viewmodels.MainActivityViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final Retrofit retrofit = RetrofitClient.getInstance();
    private final FCMTokenAPI fcmTokenAPI = retrofit.create(FCMTokenAPI.class);
    private final static String TAG = "MainActivity";
    private CompositeDisposable mCompositeDisposable;
    private MainActivityViewModel viewModel;
    private int assignmentId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mCompositeDisposable = new CompositeDisposable();
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        Intent intent = getIntent();
        int classId = intent.getIntExtra("classId", 0);
        assignmentId = intent.getIntExtra("assignmentId", 0);
        viewModel.setClassId(classId, new MainActivityViewModel.CallBack() {
            @Override
            public void getGroupIdSuccess(int groupId) {
                Log.d(TAG, "getGroupIdSuccess: " + groupId);
                AssignmentDetailFragment fragment = AssignmentDetailFragment.newInstance(groupId,assignmentId);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();

    }
}