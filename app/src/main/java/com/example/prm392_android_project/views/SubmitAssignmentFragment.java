package com.example.prm392_android_project.views;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.databinding.FragmentSubmitAssignmentBinding;
import com.example.prm392_android_project.viewmodels.SubmissionViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SubmitAssignmentFragment extends DialogFragment {

    public static String TAG = "SubmitAssignmentFragment";
    private static int assignmentId;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private SubmissionViewModel viewModel;
    private FragmentSubmitAssignmentBinding binding;
    private SharedPreferences studentSharedPref;
    private int studentId;

    public SubmitAssignmentFragment() {
        // Required empty public constructor
    }

    public static SubmitAssignmentFragment newInstance(int id) {
        assignmentId = id;
        return new SubmitAssignmentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        studentSharedPref = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        studentId =studentSharedPref.getInt("userId",-1);
        viewModel = new ViewModelProvider(requireActivity()).get(SubmissionViewModel.class);
        viewModel.initialSetGroupIdAndAssignmentId(assignmentId);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_submit_assignment,
                container,
                false
        );
        viewModel.getAssignmentSubmission().observe(getViewLifecycleOwner(), assignmentSubmission -> binding.setSubmission(assignmentSubmission));
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //thay bằng id người dùng
        binding.btnSubmit.setOnClickListener(v -> {
            viewModel.addAssignmentSubmission(studentId);
            dismiss();
        });

        binding.btnCancelSubmit.setOnClickListener(v -> {
            viewModel.cancelAssignmentSubmission(studentId);
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
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            int width = (int) (requireContext().getResources().getDisplayMetrics().widthPixels * 0.9);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setLayout(width, height);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        binding = null;
    }
}