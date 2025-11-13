package com.example.prm392_android_project.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.dtos.AddMemberToGroupRequest;
import com.example.prm392_android_project.dtos.CreateAssignmentRequest;
import com.example.prm392_android_project.dtos.CreateGroupRequest;
import com.example.prm392_android_project.models.AssignmentModel;
import com.example.prm392_android_project.models.ClassDetailTeacherViewModel;
import com.example.prm392_android_project.models.ClassMemberModel;
import com.example.prm392_android_project.models.GroupGradeModel;
import com.example.prm392_android_project.models.GroupModel;
import com.example.prm392_android_project.recyclerviewadapter.GroupSelectionAdapter;
import com.example.prm392_android_project.recyclerviewadapter.TeacherAssignmentAdapter;
import com.example.prm392_android_project.recyclerviewadapter.TeacherGroupAdapter;
import com.example.prm392_android_project.retrofit.API.TeacherClassDetailApi;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherClassDetailActivity extends AppCompatActivity
        implements TeacherAssignmentAdapter.OnAssignmentClickListener,
        TeacherGroupAdapter.OnGroupClickListener {

    private ProgressBar progressBar;
    private TextView tvClassName, tvTeacherName, tvClassCode;
    private RecyclerView rvAssignments, rvGroups;
    private FloatingActionButton fabAddAssignment, fabAddGroup;
    private TeacherAssignmentAdapter assignmentAdapter;
    private TeacherGroupAdapter groupAdapter;
    private TeacherClassDetailApi teacherApi;
    private String selectedDeadlineString = null;

    private int currentClassId = -1;
    private int currentUserId = -1;

    private ClassDetailTeacherViewModel currentClassData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_detail);

        teacherApi = RetrofitClient2.getClient().create(TeacherClassDetailApi.class);

        progressBar = findViewById(R.id.progress_bar);
        tvClassName = findViewById(R.id.tv_class_name);
        tvTeacherName = findViewById(R.id.tv_teacher_name);
        tvClassCode = findViewById(R.id.tv_class_code);
        rvAssignments = findViewById(R.id.recycler_view_assignments);
        rvGroups = findViewById(R.id.recycler_view_groups);
        fabAddAssignment = findViewById(R.id.fab_add_assignment);
        fabAddGroup = findViewById(R.id.fab_add_group);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Quản lý Lớp học");
        }
        setupRecyclerViews();
        setupClickListeners();

        currentClassId = getSharedPreferences("CLASS_ID", Context.MODE_PRIVATE).getInt("classId", -1);
        currentUserId = getSharedPreferences("pref", Context.MODE_PRIVATE).getInt("userId", -1);

        if (currentClassId != -1 && currentUserId != -1) {
            loadClassDetails(currentClassId, currentUserId);
        } else {
            Toast.makeText(this, "Không tìm thấy Class ID hoặc User ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupRecyclerViews() {
        rvAssignments.setLayoutManager(new LinearLayoutManager(this));
        assignmentAdapter = new TeacherAssignmentAdapter(new ArrayList<>(), this);
        rvAssignments.setAdapter(assignmentAdapter);

        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        groupAdapter = new TeacherGroupAdapter(this, new ArrayList<>(), this);
        rvGroups.setAdapter(groupAdapter);
    }

    private void setupClickListeners() {
        fabAddAssignment.setOnClickListener(v -> showCreateAssignmentDialog());
        fabAddGroup.setOnClickListener(v -> showCreateGroupDialog());
    }

    private void loadClassDetails(int classId, int userId) {
        progressBar.setVisibility(View.VISIBLE);
        teacherApi.getClassDetail(classId, userId).enqueue(new Callback<ClassDetailTeacherViewModel>() {
            @Override
            public void onResponse(Call<ClassDetailTeacherViewModel> call, Response<ClassDetailTeacherViewModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    currentClassData = response.body();
                    tvClassName.setText(currentClassData.getClassName());
                    tvClassCode.setText("Mã lớp: " + currentClassData.getClassCode());
                    if (currentClassData.getTeacher() != null) {
                        tvTeacherName.setText(currentClassData.getTeacher().getFirstName() + " " + currentClassData.getTeacher().getLastName());
                    }
                    assignmentAdapter = new TeacherAssignmentAdapter(currentClassData.getAssignments(), TeacherClassDetailActivity.this);
                    rvAssignments.setAdapter(assignmentAdapter);
                    groupAdapter = new TeacherGroupAdapter(TeacherClassDetailActivity.this, currentClassData.getGroups(), TeacherClassDetailActivity.this);
                    rvGroups.setAdapter(groupAdapter);
                } else {
                    Toast.makeText(TeacherClassDetailActivity.this, "Lỗi tải dữ liệu: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ClassDetailTeacherViewModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(TeacherClassDetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(AssignmentModel assignment) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa bài tập '" + assignment.getTitle() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    progressBar.setVisibility(View.VISIBLE);
                    // SỬA: Thêm currentUserId
                    teacherApi.deleteAssignment(assignment.getId(), currentUserId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(TeacherClassDetailActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            loadClassDetails(currentClassId, currentUserId); // SỬA
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TeacherClassDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onAssignmentClick(AssignmentModel assignment) {
        showGroupSelectionDialog(assignment);
    }

    @Override
    public void onDeleteGroupClick(GroupModel group) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa '" + group.getGroupName() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    progressBar.setVisibility(View.VISIBLE);
                    // SỬA: Thêm currentUserId
                    teacherApi.deleteGroup(group.getGroupId(), currentUserId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(TeacherClassDetailActivity.this, "Xóa nhóm thành công", Toast.LENGTH_SHORT).show();
                            loadClassDetails(currentClassId, currentUserId); // SỬA
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TeacherClassDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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
                .setTitle("Xác nhận")
                .setMessage("Xóa " + member.getFirstName() + " khỏi " + group.getGroupName() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    progressBar.setVisibility(View.VISIBLE);
                    // SỬA: Thêm currentUserId
                    teacherApi.removeStudentFromGroup(group.getGroupId(), member.getUserId(), currentUserId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(TeacherClassDetailActivity.this, "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                            loadClassDetails(currentClassId, currentUserId); // SỬA
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TeacherClassDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showCreateAssignmentDialog() {
        selectedDeadlineString = null;

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_assignment, null);
        EditText etTitle = dialogView.findViewById(R.id.et_assignment_title);
        EditText etDescription = dialogView.findViewById(R.id.et_assignment_description);

        Button btnPickDeadline = dialogView.findViewById(R.id.btn_pick_deadline);
        TextView tvSelectedDeadline = dialogView.findViewById(R.id.tv_selected_deadline);

        btnPickDeadline.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {

                        String displayDate = String.format(Locale.getDefault(), "Hạn nộp: %02d/%02d/%d",
                                selectedDayOfMonth, selectedMonth + 1, selectedYear);
                        tvSelectedDeadline.setText(displayDate);

                        selectedDeadlineString = String.format(Locale.getDefault(), "%d-%02d-%02dT00:00:00",
                                selectedYear, selectedMonth + 1, selectedDayOfMonth);
                    }, year, month, day);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Tạo", (dialog, which) -> {
                    String title = etTitle.getText().toString().trim();
                    String description = etDescription.getText().toString().trim();
                    if (title.isEmpty()) {
                        Toast.makeText(this, "Tiêu đề không được trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    CreateAssignmentRequest request = new CreateAssignmentRequest(
                            title,
                            description,
                            selectedDeadlineString,
                            false,
                            currentUserId
                    );

                    progressBar.setVisibility(View.VISIBLE);
                    teacherApi.createAssignment(currentClassId, request).enqueue(new Callback<AssignmentModel>() {
                        @Override
                        public void onResponse(Call<AssignmentModel> call, Response<AssignmentModel> response) {
                            Toast.makeText(TeacherClassDetailActivity.this, "Tạo bài tập thành công", Toast.LENGTH_SHORT).show();
                            loadClassDetails(currentClassId, currentUserId); // Tải lại
                        }
                        @Override
                        public void onFailure(Call<AssignmentModel> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TeacherClassDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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
                        Toast.makeText(this, "Tên nhóm không được trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    CreateGroupRequest request = new CreateGroupRequest(groupName, currentUserId);

                    progressBar.setVisibility(View.VISIBLE);
                    teacherApi.createGroup(currentClassId, request).enqueue(new Callback<GroupModel>() {
                        @Override
                        public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                Toast.makeText(TeacherClassDetailActivity.this, "Tạo nhóm thành công", Toast.LENGTH_SHORT).show();
                                loadClassDetails(currentClassId, currentUserId); // SỬA
                            } else {
                                Toast.makeText(TeacherClassDetailActivity.this, "Lỗi khi tạo nhóm: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<GroupModel> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TeacherClassDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showAddMemberDialog(GroupModel group) {
        if (currentClassData == null) return;
        List<ClassMemberModel> allStudents = currentClassData.getAllStudents();
        List<Integer> studentsInGroups = currentClassData.getGroups().stream()
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
                    progressBar.setVisibility(View.VISIBLE);

                    AddMemberToGroupRequest request = new AddMemberToGroupRequest(selectedStudent.getUserId(), currentUserId);

                    teacherApi.addStudentToGroup(group.getGroupId(), request).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(TeacherClassDetailActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            loadClassDetails(currentClassId, currentUserId); // SỬA
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TeacherClassDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showGroupSelectionDialog(AssignmentModel assignment) {
        if (currentClassData == null || currentClassData.getGroups() == null) {
            Toast.makeText(this, "Dữ liệu nhóm chưa tải", Toast.LENGTH_SHORT).show();
            return;
        }
        List<GroupModel> groups = currentClassData.getGroups();
        if (groups.isEmpty()) {
            Toast.makeText(this, "Lớp này chưa có nhóm", Toast.LENGTH_SHORT).show();
            return;
        }

        List<GroupGradeModel> grades = assignment.getGroupGrades();

        List<GroupSelectionAdapter.GroupWithGrade> groupListWithGrades = new ArrayList<>();

        for (GroupModel group : groups) {
            String gradeDisplay = "Chưa có điểm";
            if (grades != null) {
                for (GroupGradeModel gg : grades) {
                    if (gg.getGroupId() == group.getGroupId() && gg.getGrade() != null) {
                        gradeDisplay = gg.getGrade().toString();
                        break;
                    }
                }
            }
            groupListWithGrades.add(new GroupSelectionAdapter.GroupWithGrade(group, gradeDisplay));
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_select_group, null);

        TextView tvDialogTitle = dialogView.findViewById(R.id.tv_dialog_title);
        RecyclerView rvGroupSelection = dialogView.findViewById(R.id.rv_group_selection);

        tvDialogTitle.setText("Chấm điểm cho: " + assignment.getTitle());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setNegativeButton("Hủy", null)
                .create();

        GroupSelectionAdapter adapter = new GroupSelectionAdapter(groupListWithGrades, selectedGroup -> {
            int assignmentId = assignment.getId();
            int groupId = selectedGroup.getGroupId();

            SharedPreferences pref = this.getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("assignmentId", assignmentId);
            editor.putInt("groupId", groupId);
            editor.putInt("classId", currentClassId);
            editor.apply();

            Log.d("TeacherClassDetail", "Đã lưu Assignment ID: " + assignmentId + " và Group ID: " + groupId);

            Intent intent = new Intent(this, GradingActivity.class);
            startActivity(intent);

            dialog.dismiss();
        });

        rvGroupSelection.setLayoutManager(new LinearLayoutManager(this));
        rvGroupSelection.setAdapter(adapter);

        dialog.show();
    }
}