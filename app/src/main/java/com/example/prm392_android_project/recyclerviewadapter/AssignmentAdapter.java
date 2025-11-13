package com.example.prm392_android_project.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.AssignmentModel;
import com.example.prm392_android_project.views.AssignmentDetailFragment;
import com.example.prm392_android_project.views.MainActivity;
import com.example.prm392_android_project.views.StudentClassDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    public interface OnAssignmentClickListener {
        void onAssignmentClick(AssignmentModel assignment);
    }

    private final List<AssignmentModel> assignmentList;
    private final OnAssignmentClickListener clickListener;

    // Constructor nhận List
    public AssignmentAdapter(List<AssignmentModel> assignmentList, OnAssignmentClickListener listener) {
        this.assignmentList = assignmentList;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assignment, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        AssignmentModel currentAssignment = assignmentList.get(position);
        holder.bind(currentAssignment, clickListener);
    }

    @Override
    public int getItemCount() {
        return assignmentList != null ? assignmentList.size() : 0;
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription, tvDeadline, tvGrade;
        private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        private SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_assignment_title);
            tvDescription = itemView.findViewById(R.id.tv_assignment_description);
            tvDeadline = itemView.findViewById(R.id.tv_assignment_deadline);
            tvGrade = itemView.findViewById(R.id.tv_assignment_grade);
        }

        public void bind(AssignmentModel assignment, OnAssignmentClickListener listener) {
            tvTitle.setText(assignment.getTitle());
            tvDescription.setText(assignment.getDescription());

            // Format ngày tháng
            if (assignment.getDeadline() != null) {
                try {
                    Date date = apiDateFormat.parse(assignment.getDeadline());
                    tvDeadline.setText("Hạn nộp: " + displayDateFormat.format(date));
                } catch (ParseException e) {
                    tvDeadline.setText("Hạn nộp: " + assignment.getDeadline());
                }
            } else {
                tvDeadline.setText("Không có hạn nộp");
            }

            // Bind điểm
            if (assignment.getStudentGradeDisplay() != null && !assignment.getStudentGradeDisplay().isEmpty()) {
                tvGrade.setText(assignment.getStudentGradeDisplay());
            } else {
                tvGrade.setText("Chưa có điểm");
            }

            itemView.setOnClickListener(v -> listener.onAssignmentClick(assignment));
        }
    }
}
