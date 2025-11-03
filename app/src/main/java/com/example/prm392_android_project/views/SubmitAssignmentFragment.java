package com.example.prm392_android_project.views;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public SubmitAssignmentFragment() {
        // Required empty public constructor
    }

    public static SubmitAssignmentFragment newInstance(int id) {
        assignmentId = id;
        return new SubmitAssignmentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
            viewModel.addAssignmentSubmission(2);
            dismiss();
        });

        binding.btnCancelSubmit.setOnClickListener(v -> {
            viewModel.cancelAssignmentSubmission(2);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        binding = null;
    }
}