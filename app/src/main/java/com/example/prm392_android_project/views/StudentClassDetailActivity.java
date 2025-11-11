package com.example.prm392_android_project.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.GroupModel;
import com.example.prm392_android_project.recyclerviewadapter.AssignmentAdapter;
import com.example.prm392_android_project.recyclerviewadapter.GroupAdapter;
import com.example.prm392_android_project.viewmodels.StudentClassDetailViewModel;

public class StudentClassDetailActivity extends AppCompatActivity implements GroupAdapter.OnGroupButtonClickListener{
    private StudentClassDetailViewModel viewModel;

    private ProgressBar progressBar;
    private TextView tvClassName;
    private TextView tvTeacherName;
    private RecyclerView rvAssignments;
    private RecyclerView rvGroups;

    private AssignmentAdapter assignmentAdapter;
    private GroupAdapter groupAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_detail);

        viewModel = new ViewModelProvider(this).get(StudentClassDetailViewModel.class);

        progressBar = findViewById(R.id.progress_bar);
        tvClassName = findViewById(R.id.tv_class_name);
        tvTeacherName = findViewById(R.id.tv_teacher_name);
        rvAssignments = findViewById(R.id.recycler_view_assignments);
        rvGroups = findViewById(R.id.recycler_view_groups);

        setupRecyclerViews();

        setupObservers();

        int classIdToLoad = this.getSharedPreferences("CLASS_ID", MODE_PRIVATE).getInt("classId", -1);
        if (classIdToLoad != -1) {
            viewModel.fetchClassDetail(classIdToLoad);
        } else {
            Toast.makeText(this, "Không có Class ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerViews() {
        rvAssignments.setLayoutManager(new LinearLayoutManager(this));
        assignmentAdapter = new AssignmentAdapter();
        rvAssignments.setAdapter(assignmentAdapter);

        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        groupAdapter = new GroupAdapter(this);
        rvGroups.setAdapter(groupAdapter);
    }

    private void setupObservers() {
        viewModel.isLoading.observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.error.observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.classDetail.observe(this, classDetail -> {
            if (classDetail != null) {
                tvClassName.setText(classDetail.getClassName());

                if (classDetail.getTeacher() != null) {
                    String teacherName = classDetail.getTeacher().getFirstName() + " " + classDetail.getTeacher().getLastName();
                    tvTeacherName.setText(teacherName);
                }

                groupAdapter.setCurrentUserGroupId(classDetail.getCurrentUserGroupId());

                assignmentAdapter.submitList(classDetail.getAssignments());
                groupAdapter.submitList(classDetail.getGroups());
            }
        });

        viewModel.actionSuccess.observe(this, isSuccess -> {
            if (Boolean.TRUE.equals(isSuccess)) {
                Toast.makeText(this, "Hành động thành công!", Toast.LENGTH_SHORT).show();

                int classIdToLoad = this.getSharedPreferences("CLASS_ID", MODE_PRIVATE).getInt("classId", -1);
                if(classIdToLoad != -1) viewModel.fetchClassDetail(classIdToLoad);

                viewModel.resetActionSuccess();
            }
        });
    }

    @Override
    public void onJoinClick(GroupModel group) {
        viewModel.joinGroup(group.getGroupId());
    }

    @Override
    public void onLeaveClick(GroupModel group) {
        viewModel.leaveGroup(group.getGroupId());
    }
}
