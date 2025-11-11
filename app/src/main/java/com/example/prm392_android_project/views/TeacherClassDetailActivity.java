package com.example.prm392_android_project.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.dtos.CreateAssignmentRequest;
import com.example.prm392_android_project.dtos.CreateGroupRequest;
import com.example.prm392_android_project.models.AssignmentModel;
import com.example.prm392_android_project.models.ClassMemberModel;
import com.example.prm392_android_project.models.GroupModel;
import com.example.prm392_android_project.recyclerviewadapter.TeacherAssignmentAdapter;
import com.example.prm392_android_project.recyclerviewadapter.TeacherGroupAdapter;
import com.example.prm392_android_project.viewmodels.TeacherClassDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherClassDetailActivity extends AppCompatActivity
        implements TeacherAssignmentAdapter.OnAssignmentClickListener,
        TeacherGroupAdapter.OnGroupClickListener {
    private TeacherClassDetailViewModel viewModel;

    private ProgressBar progressBar;
    private TextView tvClassName, tvTeacherName, tvClassCode;
    private RecyclerView rvAssignments, rvGroups;
    private FloatingActionButton fabAddAssignment, fabAddGroup;
    private TeacherAssignmentAdapter assignmentAdapter;
    private TeacherGroupAdapter groupAdapter;

    private int currentClassId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_detail);

        viewModel = new ViewModelProvider(this).get(TeacherClassDetailViewModel.class);

        progressBar = findViewById(R.id.progress_bar);
        tvClassName = findViewById(R.id.tv_class_name);
        tvTeacherName = findViewById(R.id.tv_teacher_name);
        tvClassCode = findViewById(R.id.tv_class_code);
        rvAssignments = findViewById(R.id.recycler_view_assignments);
        rvGroups = findViewById(R.id.recycler_view_groups);
        fabAddAssignment = findViewById(R.id.fab_add_assignment);
        fabAddGroup = findViewById(R.id.fab_add_group);

        setupRecyclerViews();
        setupClickListeners();
        setupObservers();

        currentClassId = this.getSharedPreferences("CLASS_ID",MODE_PRIVATE).getInt("classId", -1);
        if (currentClassId != -1) {
            viewModel.fetchClassDetail(currentClassId);
        } else {
            Toast.makeText(this, "Không có Class ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupRecyclerViews() {
        rvAssignments.setLayoutManager(new LinearLayoutManager(this));
        assignmentAdapter = new TeacherAssignmentAdapter(this);
        rvAssignments.setAdapter(assignmentAdapter);

        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        groupAdapter = new TeacherGroupAdapter(this, this);
        rvGroups.setAdapter(groupAdapter);
    }


    private void setupClickListeners() {
        fabAddAssignment.setOnClickListener(v -> showCreateAssignmentDialog());

        fabAddGroup.setOnClickListener(v -> showCreateGroupDialog());
    }

    @Override
    public void onDeleteClick(AssignmentModel assignment) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa bài tập '" + assignment.getTitle() + "' không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    viewModel.deleteAssignment(assignment.getId());
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onDeleteGroupClick(GroupModel group) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa '" + group.getGroupName() + "' không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    viewModel.deleteGroup(group.getGroupId());
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onAddMemberClick(GroupModel group) {
        showAddMemberDialog(group);
    }

    @Override
    public void onRemoveMemberClick(GroupModel group, ClassMemberModel member) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa thành viên")
                .setMessage("Xóa " + member.getFirstName() + " khỏi " + group.getGroupName() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    viewModel.removeStudentFromGroup(group.getGroupId(), member.getUserId());
                })
                .setNegativeButton("Hủy", null)
                .show();
    }


    private void showCreateAssignmentDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_assignment, null);
        EditText etTitle = dialogView.findViewById(R.id.et_assignment_title);
        EditText etDescription = dialogView.findViewById(R.id.et_assignment_description);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Tạo", (dialog, which) -> {
                    String title = etTitle.getText().toString().trim();
                    String description = etDescription.getText().toString().trim();

                    if (title.isEmpty()) {
                        Toast.makeText(this, "Tiêu đề không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    CreateAssignmentRequest request = new CreateAssignmentRequest(title, description, null, false);
                    viewModel.createAssignment(currentClassId, request);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showCreateGroupDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_group, null);
        EditText etGroupName = dialogView.findViewById(R.id.et_group_name);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Tạo", (dialog, which) -> {
                    String groupName = etGroupName.getText().toString().trim();
                    if (groupName.isEmpty()) {
                        Toast.makeText(this, "Tên nhóm không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CreateGroupRequest request = new CreateGroupRequest(groupName);
                    viewModel.createGroup(currentClassId, request);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showAddMemberDialog(GroupModel group) {
        if (viewModel.classDetail.getValue() == null) {
            Toast.makeText(this, "Dữ liệu lớp chưa tải xong", Toast.LENGTH_SHORT).show();
            return;
        }

        List<ClassMemberModel> allStudents = viewModel.classDetail.getValue().getAllStudents();

        List<Integer> studentsInGroups = viewModel.classDetail.getValue().getGroups().stream()
                .flatMap(g -> g.getMembers().stream())
                .map(ClassMemberModel::getUserId)
                .collect(Collectors.toList());

        List<ClassMemberModel> availableStudents = allStudents.stream()
                .filter(s -> !studentsInGroups.contains(s.getUserId()))
                .collect(Collectors.toList());

        if (availableStudents.isEmpty()) {
            Toast.makeText(this, "Tất cả sinh viên đã có nhóm", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                availableStudents.stream().map(s -> s.getFirstName() + " " + s.getLastName()).collect(Collectors.toList())
        );

        new AlertDialog.Builder(this)
                .setTitle("Thêm thành viên vào " + group.getGroupName())
                .setAdapter(adapter, (dialog, index) -> {
                    ClassMemberModel selectedStudent = availableStudents.get(index);

                    viewModel.addStudentToGroup(group.getGroupId(), selectedStudent.getUserId());
                })
                .setNegativeButton("Hủy", null)
                .show();
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
                tvClassCode.setText("Mã lớp: " + classDetail.getClassCode());
                if (classDetail.getTeacher() != null) {
                    tvTeacherName.setText(classDetail.getTeacher().getFirstName() + " " + classDetail.getTeacher().getLastName());
                }
                assignmentAdapter.submitList(classDetail.getAssignments());
                groupAdapter.submitList(classDetail.getGroups());
            }
        });

        viewModel.actionSuccess.observe(this, isSuccess -> {
            if (Boolean.TRUE.equals(isSuccess)) {
                Toast.makeText(this, "Thao tác thành công!", Toast.LENGTH_SHORT).show();
                if (currentClassId != -1) {
                    viewModel.fetchClassDetail(currentClassId);
                }
                viewModel.resetActionSuccess();
            }
        });
    }
}
