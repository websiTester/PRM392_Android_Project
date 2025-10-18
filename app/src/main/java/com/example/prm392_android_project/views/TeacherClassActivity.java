package com.example.prm392_android_project.views;

import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.ClassListDto;
import com.example.prm392_android_project.models.TeacherHomeResponse;
import com.example.prm392_android_project.recyclerviewadapter.ClassAdapter;
import com.example.prm392_android_project.retrofit.API.ITeacherClassApi;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TeacherClassActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tvClassCount;
    private ClassAdapter classAdapter;
    private final List<ClassListDto> classList = new ArrayList<>();
    private final ITeacherClassApi apiService = RetrofitClient.getInstance().create(ITeacherClassApi.class);

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView=findViewById(R.id.recyclerViewClasses);
        tvClassCount=findViewById(R.id.tvClassCount);
        Button btnCreateClass=findViewById(R.id.fabCreateClass);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_class);

    }
    private void setupRecyclerView() {
        classAdapter = new ClassAdapter(classList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(classAdapter);
    }

    private void fetchTeacherClasses(int teacherId) {
        // Gọi API bất đồng bộ
        compositeDisposable.add(apiService.getTeacherHomeData(teacherId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TeacherHomeResponse>() {
                    @Override
                    public void onSuccess(@NonNull TeacherHomeResponse response) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        // Log toàn bộ JSON mà Retrofit parse được
                        Log.d("API_RAW_JSON", gson.toJson(response));

                        List<ClassListDto> fetchedClasses = response.getClasses();
                        Log.d("API_RESPONSE_CLASSES_COUNT", "Fetched Classes Count: " + (fetchedClasses != null ? fetchedClasses.size() : 0));

                        // Cập nhật dữ liệu
                        classList.clear();
                        if (fetchedClasses != null) {
                            classList.addAll(fetchedClasses);
                        }
                        classAdapter.notifyDataSetChanged();

                        // Cập nhật số lượng lớp học
                        tvClassCount.setText("Danh sách lớp học (" + classList.size() + ")");

                        if (classList.isEmpty()) {
                            Toast.makeText(TeacherClassActivity.this, "Bạn chưa tạo lớp học nào.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("API_ERROR", "Lỗi khi tải dữ liệu lớp học", e);
                        Toast.makeText(TeacherClassActivity.this, "Không thể tải lớp học. Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        );
    }
    private void showCreateClassDialog() {
        Toast.makeText(this, "Hiển thị dialog tạo lớp học...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

}