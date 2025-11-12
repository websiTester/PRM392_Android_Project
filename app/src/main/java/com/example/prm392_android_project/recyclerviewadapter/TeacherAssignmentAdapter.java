package com.example.prm392_android_project.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.AssignmentModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TeacherAssignmentAdapter extends RecyclerView.Adapter<TeacherAssignmentAdapter.AssignmentViewHolder> {

    public interface OnAssignmentClickListener {
        void onDeleteClick(AssignmentModel assignment);
        void onAssignmentClick(AssignmentModel assignment);
    }

    private final List<AssignmentModel> assignmentList;
    private final OnAssignmentClickListener clickListener;

    public TeacherAssignmentAdapter(List<AssignmentModel> list, OnAssignmentClickListener listener) {
        super();
        this.assignmentList = list;
        this.clickListener = listener;
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDeadline;
        private ImageButton btnDelete;

        private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        private SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_assignment_title);
            tvDescription = itemView.findViewById(R.id.tv_assignment_description);
            tvDeadline = itemView.findViewById(R.id.tv_assignment_deadline);
            btnDelete = itemView.findViewById(R.id.btn_delete_assignment);
        }

        public void bind(AssignmentModel assignment, OnAssignmentClickListener listener) {
            tvTitle.setText(assignment.getTitle());
            tvDescription.setText(assignment.getDescription());

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

            btnDelete.setOnClickListener(v -> listener.onDeleteClick(assignment));

            itemView.setOnClickListener(v -> listener.onAssignmentClick(assignment));
        }
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assignment_teacher, parent, false);
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
}