package com.example.prm392_android_project.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.ClassItem;
import com.example.prm392_android_project.models.TeacherHomeResponse;
import com.example.prm392_android_project.recyclerviewadapter.TeacherClassAdapter;
import com.example.prm392_android_project.retrofit.API.TeacherClassApi;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherClassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvClassCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class);

        recyclerView = findViewById(R.id.recyclerViewClasses);
        tvClassCount = findViewById(R.id.tvClassCount);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadTeacherClasses();
    }

    private void loadTeacherClasses() {
        TeacherClassApi api = RetrofitClient2.getClient().create(TeacherClassApi.class);

        api.getTeacherHome().enqueue(new Callback<TeacherHomeResponse>() {
            @Override
            public void onResponse(Call<TeacherHomeResponse> call, Response<TeacherHomeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TeacherHomeResponse data = response.body();
                    List<ClassItem> classList = data.getClasses();

                    recyclerView.setAdapter(new TeacherClassAdapter(classList));
                    tvClassCount.setText("Danh sách lớp học (" + classList.size() + ")");
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
}