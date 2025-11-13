package com.example.prm392_android_project.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.dtos.StudentGroupActionRequest;
import com.example.prm392_android_project.models.AssignmentModel;
import com.example.prm392_android_project.models.ClassDetailStudentViewModel;
import com.example.prm392_android_project.models.GroupModel;
import com.example.prm392_android_project.recyclerviewadapter.AssignmentAdapter;
import com.example.prm392_android_project.recyclerviewadapter.GroupAdapter;
import com.example.prm392_android_project.retrofit.API.StudentClassDetailApi;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentClassDetailActivity extends AppCompatActivity
        implements GroupAdapter.OnGroupButtonClickListener,
        AssignmentAdapter.OnAssignmentClickListener {

    private ProgressBar progressBar;
    private TextView tvClassName, tvTeacherName;
    private RecyclerView rvAssignments, rvGroups;

    private AssignmentAdapter assignmentAdapter;
    private GroupAdapter groupAdapter;

    private StudentClassDetailApi studentApi;

    private int currentClassId = -1;
    private int currentUserId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_detail);

        studentApi = RetrofitClient2.getClient().create(StudentClassDetailApi.class);

        progressBar = findViewById(R.id.progress_bar);
        tvClassName = findViewById(R.id.tv_class_name);
        tvTeacherName = findViewById(R.id.tv_teacher_name);
        rvAssignments = findViewById(R.id.recycler_view_assignments);
        rvGroups = findViewById(R.id.recycler_view_groups);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Chi tiết Lớp học");
        }

        setupRecyclerViews();

        currentClassId = getSharedPreferences("CLASS_ID", Context.MODE_PRIVATE).getInt("classId", -1);
        currentUserId = getSharedPreferences("pref", Context.MODE_PRIVATE).getInt("userId", -1);



        Log.d("StudentClassDetail", "Class ID: " + currentClassId + ", User ID: " + currentUserId);

        if (currentClassId == -1 || currentUserId == -1) {
            Toast.makeText(this, "Không tìm thấy Class ID hoặc User ID", Toast.LENGTH_SHORT).show();
            finish();

        }
        loadClassDetails(currentClassId, currentUserId);





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupRecyclerViews() {
        rvAssignments.setLayoutManager(new LinearLayoutManager(this));
        assignmentAdapter = new AssignmentAdapter(new ArrayList<>(), this);
        rvAssignments.setAdapter(assignmentAdapter);

        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        groupAdapter = new GroupAdapter(new ArrayList<>(), this);
        rvGroups.setAdapter(groupAdapter);
    }

    private void loadClassDetails(int classId, int userId) {
        progressBar.setVisibility(View.VISIBLE);

        studentApi.getClassDetail(classId, userId).enqueue(new Callback<ClassDetailStudentViewModel>() {
            @Override
            public void onResponse(Call<ClassDetailStudentViewModel> call, Response<ClassDetailStudentViewModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ClassDetailStudentViewModel classDetail = response.body();
                    tvClassName.setText(classDetail.getClassName());
                    if (classDetail.getTeacher() != null) {
                        tvTeacherName.setText(classDetail.getTeacher().getFirstName() + " " + classDetail.getTeacher().getLastName());
                    }
                    assignmentAdapter = new AssignmentAdapter(classDetail.getAssignments(), StudentClassDetailActivity.this);
                    rvAssignments.setAdapter(assignmentAdapter);
                    groupAdapter = new GroupAdapter(classDetail.getGroups(), StudentClassDetailActivity.this);
                    groupAdapter.setCurrentUserGroupId(classDetail.getCurrentUserGroupId());
                    rvGroups.setAdapter(groupAdapter);
                } else {
                    Toast.makeText(StudentClassDetailActivity.this, "Lỗi tải dữ liệu: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ClassDetailStudentViewModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StudentClassDetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onAssignmentClick(AssignmentModel assignment) {
        int assignmentId = assignment.getId();
        SharedPreferences pref = this.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("assignmentId", assignmentId);
        editor.apply();
        Log.d("onAssignmentClick", "Đã lưu Assignment ID: " + assignmentId + " vào file 'pref'");

         Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);

        Toast.makeText(this, "Mở bài tập (ID: " + assignmentId + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onJoinClick(GroupModel group) {
        progressBar.setVisibility(View.VISIBLE);
        StudentGroupActionRequest request = new StudentGroupActionRequest(currentUserId);

        studentApi.joinGroup(group.getGroupId(), request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(StudentClassDetailActivity.this, "Tham gia nhóm thành công!", Toast.LENGTH_SHORT).show();
                    loadClassDetails(currentClassId, currentUserId); // Tải lại dữ liệu
                } else {
                    Toast.makeText(StudentClassDetailActivity.this, "Lỗi: Bạn có thể đã ở nhóm khác", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StudentClassDetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLeaveClick(GroupModel group) {
        progressBar.setVisibility(View.VISIBLE);
        StudentGroupActionRequest request = new StudentGroupActionRequest(currentUserId);

        studentApi.leaveGroup(group.getGroupId(), request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(StudentClassDetailActivity.this, "Rời nhóm thành công!", Toast.LENGTH_SHORT).show();
                    loadClassDetails(currentClassId, currentUserId); // Tải lại dữ liệu
                } else {
                    Toast.makeText(StudentClassDetailActivity.this, "Lỗi khi rời nhóm", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StudentClassDetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}