package com.example.prm392_android_project.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prm392_android_project.models.ClassDetailStudentViewModel;
import com.example.prm392_android_project.retrofit.API.StudentClassDetailApi;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StudentClassDetailViewModel extends ViewModel {
    private StudentClassDetailApi studentApi;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<ClassDetailStudentViewModel> _classDetail = new MutableLiveData<>();
    public LiveData<ClassDetailStudentViewModel> classDetail = _classDetail;

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    // SỬA LỖI 1: Sửa typo (nếu bạn copy lỗi này từ file Teacher)
    private MutableLiveData<Boolean> _actionSuccess = new MutableLiveData<>();
    public LiveData<Boolean> actionSuccess = _actionSuccess;

    public StudentClassDetailViewModel() {
        studentApi = RetrofitClient.getInstance().create(StudentClassDetailApi.class);
    }

    public void fetchClassDetail(int classId) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                studentApi.getClassDetail(classId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    _classDetail.setValue(result);
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi tải dữ liệu: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void joinGroup(int groupId) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                studentApi.joinGroup(groupId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // SỬA LỖI 2: Thêm "(voidResult) ->"
                                (voidResult) -> {
                                    _actionSuccess.setValue(true);
                                    _isLoading.setValue(false);
                                    // Bạn có thể gọi fetchClassDetail(classId) ở đây để refresh
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi tham gia nhóm: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void leaveGroup(int groupId) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                studentApi.leaveGroup(groupId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // SỬA LỖI 2: Thêm "(voidResult) ->"
                                (voidResult) -> {
                                    _actionSuccess.setValue(true);
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi rời nhóm: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void resetActionSuccess() {
        _actionSuccess.setValue(false);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
