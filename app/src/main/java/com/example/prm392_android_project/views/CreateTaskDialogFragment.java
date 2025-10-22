package com.example.prm392_android_project.views;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.viewmodels.AssignmentDetailViewModel;

public class CreateTaskDialogFragment extends DialogFragment {
    public static String TAG = "CreateTaskDialog";
    private AssignmentDetailViewModel viewModel;

    // Phương thức tĩnh để tạo instance mới
    public static CreateTaskDialogFragment newInstance() {
        return new CreateTaskDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(AssignmentDetailViewModel.class);
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = view.findViewById(R.id.spinner_assign_to);

        String[] members = new String[]{"-- Chọn thành viên --", "Thành viên A", "Thành viên B", "Thành viên C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                members
        );
        spinner.setAdapter(adapter);

        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            String taskName = view.findViewById(R.id.et_task_name).toString();
            int points = Integer.parseInt(view.findViewById(R.id.et_point).toString());
            int assignedTo = spinner.getSelectedItemPosition();

            viewModel.laterSetDataToaddingGroupTask(taskName, points, assignedTo);
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
}
