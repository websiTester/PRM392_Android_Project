package com.example.prm392_android_project.views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.actionbindings.StudentClassAdapter;
import com.example.prm392_android_project.dtos.JoinClassRequest;
import com.example.prm392_android_project.models.StudentClassItem;
import com.example.prm392_android_project.retrofit.API.StudentClassApi;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentClassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvClassCount;
    private FloatingActionButton fabJoinClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class);

        recyclerView = findViewById(R.id.recyclerViewClasses);
        tvClassCount = findViewById(R.id.tvClassCount);
        fabJoinClass = findViewById(R.id.fabJoinClass);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        fabJoinClass.setOnClickListener(v -> showJoinDialog());

        loadStudentClasses();
    }

    private void loadStudentClasses() {
        StudentClassApi api = RetrofitClient2.getClient().create(StudentClassApi.class);

        api.getStudentClasses().enqueue(new Callback<List<StudentClassItem>>() {
            @Override
            public void onResponse(Call<List<StudentClassItem>> call, Response<List<StudentClassItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<StudentClassItem> list = response.body();
                    recyclerView.setAdapter(new StudentClassAdapter(list));
                    tvClassCount.setText("Danh sách lớp học (" + list.size() + ")");
                } else {
                    Toast.makeText(StudentClassActivity.this, "Không thể tải danh sách lớp", Toast.LENGTH_SHORT).show();
                    Log.e("API_GET_CLASS", "Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<StudentClassItem>> call, Throwable t) {
                Toast.makeText(StudentClassActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_GET_CLASS", "onFailure", t);
            }
        });
    }

    private void showJoinDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_join_class, null);
        TextInputEditText inputClassCode = dialogView.findViewById(R.id.inputClassCode);
        Button btnJoin = dialogView.findViewById(R.id.btnJoinClass);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        btnJoin.setOnClickListener(v -> {
            String code = inputClassCode.getText().toString().trim();
            if (code.isEmpty()) {
                inputClassCode.setError("Vui lòng nhập mã lớp");
                return;
            }

            joinClassApi(code, dialog);
        });

        dialog.show();
    }

    private void joinClassApi(String code, AlertDialog dialog) {
        StudentClassApi api = RetrofitClient2.getClient().create(StudentClassApi.class);

        JoinClassRequest request = new JoinClassRequest(code);
        Log.d("API_JOIN_BODY", new com.google.gson.Gson().toJson(request));

        api.joinClass(request).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject res = response.body();
                    boolean success = res.get("success").getAsBoolean();
                    String message = res.get("message").getAsString();

                    Toast.makeText(StudentClassActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (success) {
                        dialog.dismiss();
                        loadStudentClasses(); // refresh list
                    }
                } else {
                    Toast.makeText(StudentClassActivity.this, "Không thể tham gia lớp (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                    Log.e("API_JOIN", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(StudentClassActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_JOIN", "onFailure", t);
            }
        });
    }
}