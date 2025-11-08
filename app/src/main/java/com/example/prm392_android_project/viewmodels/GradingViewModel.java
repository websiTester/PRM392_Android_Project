package com.example.prm392_android_project.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prm392_android_project.models.GradingModel;
import com.example.prm392_android_project.models.MemberGradingModel;
import com.example.prm392_android_project.retrofit.API.GradingAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GradingViewModel extends ViewModel {
    private final GradingAPI gradingAPI;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public MutableLiveData<GradingModel> gradingData = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>();

    public GradingViewModel() {
        gradingAPI = RetrofitClient.getInstance().create(GradingAPI.class);
    }

    // 🔹 Lấy thông tin chấm điểm nhóm
    public void fetchGrading(int groupId, int assignmentId) {
        disposables.add(
                gradingAPI.getGradingDetails(groupId, assignmentId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> gradingData.setValue(data),
                                error -> message.setValue("Lỗi tải dữ liệu: " + error.getMessage())
                        )
        );
    }

    // 🔹 Lưu đánh giá nhóm
    public void saveGroupGrade(GradingModel model) {
        disposables.add(
                gradingAPI.saveGroupGrade(model)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> message.setValue("Lưu đánh giá nhóm thành công!"),
                                error -> message.setValue("Lỗi khi lưu: " + error.getMessage())
                        )
        );
    }

    // 🔹 Lưu đánh giá thành viên
    public void saveMemberGrades(GradingModel gradingModel, int teacherId) {
        // Log body gửi lên cho chắc
        Log.d("API_REQUEST", "save-member-grades body = " + new Gson().toJson(gradingModel));

        disposables.add(
                gradingAPI.saveMemberGrades(gradingModel, teacherId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> message.setValue("✅ Lưu đánh giá thành viên thành công!"),
                                error -> {
                                    Log.e("API_ERROR", "❌ Lỗi khi lưu đánh giá thành viên: " + error.getMessage(), error);
                                    message.setValue("❌ Lỗi khi lưu đánh giá thành viên: " + error.getMessage());
                                }
                        )
        );
    }


    protected void onCleared() {
        disposables.clear();
    }
}
