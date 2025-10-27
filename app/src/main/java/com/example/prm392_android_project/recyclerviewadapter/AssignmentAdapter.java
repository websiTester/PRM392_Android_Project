package com.example.prm392_android_project.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.AssignmentModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssignmentAdapter extends ListAdapter<AssignmentModel, AssignmentAdapter.AssignmentViewHolder> {
    private static final DiffUtil.ItemCallback<AssignmentModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AssignmentModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull AssignmentModel oldItem, @NonNull AssignmentModel newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull AssignmentModel oldItem, @NonNull AssignmentModel newItem) {
                    // So sánh nội dung, ví dụ: title và description
                    return oldItem.getTitle().equals(newItem.getTitle()) &&
                            oldItem.getDescription().equals(newItem.getDescription());
                }
            };

    public AssignmentAdapter() {
        super(DIFF_CALLBACK);
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDeadline;

        private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        private SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_assignment_title);
            tvDescription = itemView.findViewById(R.id.tv_assignment_description);
            tvDeadline = itemView.findViewById(R.id.tv_assignment_deadline);
        }

        public void bind(AssignmentModel assignment) {
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
        }
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
        AssignmentModel currentAssignment = getItem(position);
        holder.bind(currentAssignment);
    }
}
