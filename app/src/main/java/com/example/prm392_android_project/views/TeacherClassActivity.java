package com.example.prm392_android_project.views;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.prm392_android_project.R;
import com.example.prm392_android_project.dtos.CreateClassRequest;
import com.example.prm392_android_project.models.ClassItem;
import com.example.prm392_android_project.models.CourseItem;
import com.example.prm392_android_project.models.TeacherHomeResponse;
import com.example.prm392_android_project.recyclerviewadapter.TeacherClassAdapter;
import com.example.prm392_android_project.retrofit.API.TeacherClassApi;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherClassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvClassCount;
    private FloatingActionButton fabCreateClass;

    private List<CourseItem> allCourses = new ArrayList<>();
    private final int TEACHER_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class);

        recyclerView = findViewById(R.id.recyclerViewClasses);
        tvClassCount = findViewById(R.id.tvClassCount);
        fabCreateClass = findViewById(R.id.fabCreateClass);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));



        loadTeacherClasses();
    }


    private void loadTeacherClasses() {
        TeacherClassApi api = RetrofitClient2.getClient().create(TeacherClassApi.class);

        api.getTeacherHome(101).enqueue(new Callback<TeacherHomeResponse>() {
            @Override
            public void onResponse(Call<TeacherHomeResponse> call, Response<TeacherHomeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TeacherHomeResponse data = response.body();
                    List<ClassItem> classList = data.getClasses();
                    fabCreateClass.setOnClickListener(v -> showCreateClassDialog(data));

                    recyclerView.setAdapter(new TeacherClassAdapter(classList));
                    tvClassCount.setText("Danh sách lớp học (" + classList.size() + ")");

                    // ✅ Cập nhật FAB sau khi có dữ liệu khóa học
                    fabCreateClass.setOnClickListener(v -> showCreateClassDialog(data));
                } else {
                    Toast.makeText(TeacherClassActivity.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TeacherHomeResponse> call, Throwable t) {
                Toast.makeText(TeacherClassActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "onFailure: ", t);
            }
        });
    }

    private void showCreateClassDialog(TeacherHomeResponse teacherHomeResponse) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_class, null);

        TextInputEditText inputClassName = dialogView.findViewById(R.id.inputClassName);
        RadioGroup radioGroupCourseOption = dialogView.findViewById(R.id.radioGroupCourseOption);
        RadioButton radioExistingCourse = dialogView.findViewById(R.id.radioExistingCourse);
        RadioButton radioNewCourse = dialogView.findViewById(R.id.radioNewCourse);
        LinearLayout layoutExistingCourse = dialogView.findViewById(R.id.layoutExistingCourse);
        LinearLayout layoutNewCourse = dialogView.findViewById(R.id.layoutNewCourse);
        Spinner spinnerExistingCourse = dialogView.findViewById(R.id.spinnerExistingCourse);
        TextInputEditText inputNewCourseName = dialogView.findViewById(R.id.inputNewCourseName);
        TextInputEditText inputNewCourseDescription = dialogView.findViewById(R.id.inputNewCourseDescription);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmitCreateClass);

        // ✅ Gán danh sách khóa học thật từ API
        List<CourseItem> courseList = teacherHomeResponse.getAllCourses();
        ArrayAdapter<CourseItem> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                courseList
        );
        spinnerExistingCourse.setAdapter(adapter);

        radioGroupCourseOption.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioExistingCourse) {
                layoutExistingCourse.setVisibility(View.VISIBLE);
                layoutNewCourse.setVisibility(View.GONE);
            } else {
                layoutExistingCourse.setVisibility(View.GONE);
                layoutNewCourse.setVisibility(View.VISIBLE);
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        btnSubmit.setOnClickListener(v -> {
            String className = inputClassName.getText().toString().trim();

            if (className.isEmpty()) {
                inputClassName.setError("Vui lòng nhập tên lớp");
                return;
            }

            boolean isNewCourse = radioNewCourse.isChecked();
            Integer existingCourseId = null;
            String newCourseName = null;
            String newCourseDesc = null;

            if (isNewCourse) {
                newCourseName = inputNewCourseName.getText().toString().trim();
                newCourseDesc = inputNewCourseDescription.getText().toString().trim();

                if (newCourseName.isEmpty()) {
                    inputNewCourseName.setError("Vui lòng nhập tên khóa học mới");
                    return;
                }
            } else {
                CourseItem selectedCourse = (CourseItem) spinnerExistingCourse.getSelectedItem();
                if (selectedCourse != null) {
                    existingCourseId = selectedCourse.getId();
                    newCourseName = selectedCourse.getName();
                    newCourseDesc = selectedCourse.getDescription();
                } else {
                    Toast.makeText(this, "Chưa chọn khóa học!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            CreateClassRequest request = new CreateClassRequest(
                    className,
                    TEACHER_ID,
                    isNewCourse,
                    existingCourseId,
                    newCourseName,
                    newCourseDesc
            );

            // log ra để kiểm tra JSON gửi đi
            Log.d("API_CREATE_BODY", new com.google.gson.Gson().toJson(request));

            createClassApi(request, dialog);
        });

        dialog.show();
    }

    private void createClassApi(CreateClassRequest request, AlertDialog dialog) {
        TeacherClassApi api = RetrofitClient2.getClient().create(TeacherClassApi.class);

        api.createClass(request).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject res = response.body();
                    boolean success = res.get("success").getAsBoolean();
                    String message = res.get("message").getAsString();

                    Toast.makeText(TeacherClassActivity.this, message, Toast.LENGTH_SHORT).show();
                    Log.d("API_CREATE_BODY", new Gson().toJson(request));

                    if (success) {
                        dialog.dismiss();
                        Log.d("API_CREATE_BODY", new Gson().toJson(request));

                        loadTeacherClasses(); // reload lại danh sách lớp
                    }
                } else {
                    Toast.makeText(TeacherClassActivity.this, "Không thể tạo lớp (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                    Log.e("API_CREATE", "Error: " + response.code());
                    Log.d("API_CREATE_BODY", new Gson().toJson(request));

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(TeacherClassActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_CREATE", "onFailure: ", t);
            }
        });
    }
}
