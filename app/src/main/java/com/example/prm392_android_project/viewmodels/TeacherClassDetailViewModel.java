package com.example.prm392_android_project.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prm392_android_project.dtos.AddMemberToGroupRequest;
import com.example.prm392_android_project.dtos.CreateAssignmentRequest;
import com.example.prm392_android_project.dtos.CreateGroupRequest;
import com.example.prm392_android_project.models.ClassDetailTeacherViewModel;
import com.example.prm392_android_project.retrofit.API.TeacherClassDetailApi;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TeacherClassDetailViewModel extends ViewModel {
    private TeacherClassDetailApi teacherApi;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<ClassDetailTeacherViewModel> _classDetail = new MutableLiveData<>();
    public LiveData<ClassDetailTeacherViewModel> classDetail = _classDetail;

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    private MutableLiveData<Boolean> _actionSuccess = new MutableLiveData<>();
    public LiveData<Boolean> actionSuccess = _actionSuccess;

    public TeacherClassDetailViewModel() {
        teacherApi = RetrofitClient.getInstance().create(TeacherClassDetailApi.class);
    }

    public void fetchClassDetail(int classId) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                teacherApi.getClassDetail(classId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // OnSuccess
                                result -> {
                                    _classDetail.setValue(result);
                                    _isLoading.setValue(false);
                                },
                                // OnError
                                throwable -> {
                                    _error.setValue("Lỗi khi tải dữ liệu: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void createAssignment(int classId, CreateAssignmentRequest request) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                teacherApi.createAssignment(classId, request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // API trả về AssignmentModel
                                createdAssignment -> {
                                    _actionSuccess.setValue(true); // Báo hiệu thành công
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi tạo bài tập: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void updateAssignment(int assignmentId, CreateAssignmentRequest request) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                teacherApi.updateAssignment(assignmentId, request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // API trả về Void
                                (voidResult) -> {
                                    _actionSuccess.setValue(true);
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi cập nhật bài tập: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void deleteAssignment(int assignmentId) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                teacherApi.deleteAssignment(assignmentId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // API trả về Void
                                (voidResult) -> {
                                    _actionSuccess.setValue(true);
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi xóa bài tập: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void createGroup(int classId, CreateGroupRequest request) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                teacherApi.createGroup(classId, request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // API trả về GroupModel
                                createdGroup -> {
                                    _actionSuccess.setValue(true);
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi tạo nhóm: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void deleteGroup(int groupId) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                teacherApi.deleteGroup(groupId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (voidResult) -> {
                                    _actionSuccess.setValue(true);
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi xóa nhóm: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void addStudentToGroup(int groupId, int studentId) {
        _isLoading.setValue(true);
        // Tạo đối tượng request
        AddMemberToGroupRequest request = new AddMemberToGroupRequest(studentId);

        compositeDisposable.add(
                teacherApi.addStudentToGroup(groupId, request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (voidResult) -> {
                                    _actionSuccess.setValue(true);
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi thêm HS: " + throwable.getMessage());
                                    _isLoading.setValue(false);
                                }
                        )
        );
    }

    public void removeStudentFromGroup(int groupId, int studentId) {
        _isLoading.setValue(true);
        compositeDisposable.add(
                teacherApi.removeStudentFromGroup(groupId, studentId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (voidResult) -> {
                                    _actionSuccess.setValue(true);
                                    _isLoading.setValue(false);
                                },
                                throwable -> {
                                    _error.setValue("Lỗi khi xóa HS khỏi nhóm: " + throwable.getMessage());
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
