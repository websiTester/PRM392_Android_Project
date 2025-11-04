package com.example.prm392_android_project.actionbindings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.StudentClassItem;
import com.example.prm392_android_project.views.StudentClassActivity;

import java.util.List;

public class StudentClassAdapter extends RecyclerView.Adapter<StudentClassAdapter.ViewHolder>  {
    private final List<StudentClassItem> classList;

    public StudentClassAdapter(List<StudentClassItem> classList) {
        this.classList = classList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_class_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentClassItem item = classList.get(position);
        holder.tvClassName.setText(item.getClassName());
        holder.tvCourseName.setText("Khóa học: " + item.getCourseName());
        holder.tvTeacherName.setText("Giáo viên: " + item.getTeacherFullName());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName, tvCourseName, tvTeacherName;

        ViewHolder(View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
        }
    }
}
