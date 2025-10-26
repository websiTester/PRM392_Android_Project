package com.example.prm392_android_project.views;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.databinding.FragmentCreateTaskBinding;
import com.example.prm392_android_project.dtos.GetUserForDropdownResponse;
import com.example.prm392_android_project.retrofit.API.UserAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;
import com.example.prm392_android_project.viewmodels.AssignmentDetailViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CreateTaskDialogFragment extends DialogFragment {
    public static String TAG = "CreateTaskDialog";
    private final Retrofit retrofit = RetrofitClient.getInstance();
    private final UserAPI retrofitAPI = retrofit.create(UserAPI.class);
    private AssignmentDetailViewModel viewModel;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private static int groupId;
    private FragmentCreateTaskBinding binding;


    // Phương thức tĩnh để tạo instance mới
    public static CreateTaskDialogFragment newInstance(int id) {
        groupId = id;
        return new CreateTaskDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(AssignmentDetailViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_create_task,
                container,
                false
        );
        viewModel.getAddingGroupTask().observe(getViewLifecycleOwner(), addingGroupTask -> {
            if (addingGroupTask != null) {
                binding.setTask(addingGroupTask);
            }
        });
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = binding.spinnerAssignTo;
        getUserForDropdown(spinner);

        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            viewModel.addGroupTask();
            dismiss();
        });
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void getUserForDropdown(Spinner spinner){
        Disposable disposable = retrofitAPI.getUsersForDropdown(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        users -> {
                            if (users != null) {


                                ArrayAdapter<GetUserForDropdownResponse> adapter = new ArrayAdapter<>(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        users
                                );

                                spinner.setAdapter(adapter);
                                Log.d(TAG, "Fetched " + users.size() + " users successfully.");
                            } else {
                                Log.d(TAG, "Fetched 0 users or list is null.");
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "Error fetching articles: " + throwable.getMessage());
                        }
                );
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        binding = null;
    }
}
