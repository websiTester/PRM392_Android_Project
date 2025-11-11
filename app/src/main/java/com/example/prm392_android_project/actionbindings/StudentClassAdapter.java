package com.example.prm392_android_project.actionbindings;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.StudentClassItem;
import com.example.prm392_android_project.views.StudentClassActivity;
import com.example.prm392_android_project.views.StudentClassDetailActivity;
import com.example.prm392_android_project.views.TeacherClassDetailActivity;

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

        holder.studentClassCard.setOnClickListener(new View.OnClickListener() {


                                                       @Override
                                                       public void onClick(View v) {
                                                           Context context = v.getContext();
                                                           Intent intent = new Intent(context, StudentClassDetailActivity.class);
                                                           int classId=item.getClassId();
                                                           context.getSharedPreferences("CLASS_ID", Context.MODE_PRIVATE)
                                                                   .edit()
                                                                   .putInt("classId", classId)
                                                                   .apply();
                                                           Log.d("classId", String.valueOf(classId));
                                                           context.startActivity(intent);

                                                       }
                                                   }
        );

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName, tvCourseName, tvTeacherName;
         LinearLayout studentClassCard;

        ViewHolder(View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            studentClassCard=itemView.findViewById(R.id.student_class_card);
        }
    }
}
