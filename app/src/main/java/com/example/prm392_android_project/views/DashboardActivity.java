package com.example.prm392_android_project.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.actionbindings.UserAdapter;
import com.example.prm392_android_project.models.DashboardModel;
import com.example.prm392_android_project.models.UserItem;
import com.example.prm392_android_project.retrofit.API.DashboardAPI;
import com.example.prm392_android_project.retrofit.Client.DashboardClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvTotalTeacher, tvTotalStudent, tvTotalClasses, tvTotalCourses;
    private RecyclerView rvUsers;
    private UserAdapter userAdapter;
    private DashboardAPI dashboardAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);   // chính là XML bạn gửi

        dashboardAPI = DashboardClient.getDashboardAPI();

        bindViews();
        setupRecycler();

        loadDashboardFromApi();
        loadUsersFromApi();
    }

    private void bindViews() {
        tvTotalTeacher = findViewById(R.id.tvTotalTeacher);
        tvTotalStudent = findViewById(R.id.tvTotalStudent);
        tvTotalClasses = findViewById(R.id.tvTotalClasses);
        tvTotalCourses = findViewById(R.id.tvTotalCourses);
        rvUsers        = findViewById(R.id.rvUsers);
    }

    private void setupRecycler() {
        userAdapter = new UserAdapter(user -> {
            Log.d("DBG", "Clicked user id=" + user.getUserId());
            toggleUserStatus(user);
        });
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(userAdapter);
    }

    // ================== 1. GỌI GET /api/Dashboard/dashboard ==================
    private void loadDashboardFromApi() {
        dashboardAPI.getDashboard().enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call,
                                   Response<DashboardModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DashboardModel data = response.body();

                    tvTotalTeacher.setText(String.valueOf(data.getTotalTeachers()));
                    tvTotalStudent.setText(String.valueOf(data.getTotalStudents()));
                    tvTotalClasses.setText(String.valueOf(data.getTotalClasses()));
                    tvTotalCourses.setText(String.valueOf(data.getTotalCourses()));

                    Log.d("DBG", "Dashboard loaded OK");
                } else {
                    Toast.makeText(DashboardActivity.this,
                            "Lỗi tải dashboard: code " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    Log.e("DBG", "Dashboard error code=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                Toast.makeText(DashboardActivity.this,
                        "Lỗi mạng dashboard: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("DBG", "Dashboard onFailure", t);
            }
        });
    }

    // ================== 2. GỌI GET /api/Dashboard/user ==================
    private void loadUsersFromApi() {
        dashboardAPI.getUserList().enqueue(new Callback<List<UserItem>>() {
            @Override
            public void onResponse(Call<List<UserItem>> call,
                                   Response<List<UserItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UserItem> list = response.body();
                    Log.d("DBG", "API user size = " + list.size());
                    userAdapter.setData(list);
                } else {
                    Toast.makeText(DashboardActivity.this,
                            "Lỗi tải danh sách: code " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    Log.e("DBG", "User list error code=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UserItem>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this,
                        "Lỗi mạng user list: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("DBG", "User list onFailure", t);
            }
        });
    }

    // ================== 3. GỌI PUT /api/Dashboard/user/{id}/status ==================
    private void toggleUserStatus(UserItem user) {
        final boolean newStatus = !user.isActive();

        dashboardAPI.updateUserStatus(user.getUserId(), newStatus)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call,
                                           Response<Void> response) {
                        if (response.isSuccessful()) {
                            user.setActive(newStatus);
                            userAdapter.updateItem(user);

                            Toast.makeText(DashboardActivity.this,
                                    newStatus ? "Đã kích hoạt user" : "Đã khóa user",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DashboardActivity.this,
                                    "Cập nhật trạng thái thất bại: code " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                            Log.e("DBG", "Update status error code=" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(DashboardActivity.this,
                                "Lỗi mạng khi cập nhật trạng thái: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Log.e("DBG", "Update status onFailure", t);
                    }
                });
    }
}
