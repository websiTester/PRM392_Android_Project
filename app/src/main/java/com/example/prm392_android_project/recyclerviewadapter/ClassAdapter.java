package com.example.prm392_android_project.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.ClassListDto;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private final List<ClassListDto> classList;

    public ClassAdapter(List<ClassListDto> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Ánh xạ layout item_teacher_class_card.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher_class_card, parent, false);
        return new ClassViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassListDto currentClass = classList.get(position);

        // Hiển thị Tên lớp và Mã lớp
        String classTitle = currentClass.getClassName() + " (" + currentClass.getClassCode() + ")";
        holder.tvClassName.setText(classTitle);


        // Hiển thị Tên khóa học
        holder.tvCourseName.setText("Khóa học: " + currentClass.getCourseName());

        // Hiển thị Tên giảng viên
        holder.tvTeacherName.setText("GV: " + currentClass.getTeacherFullName());

        // TODO: Thêm logic xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            // Ví dụ: Mở trang chi tiết lớp học
            // Toast.makeText(v.getContext(), "Mở chi tiết lớp: " + currentClass.getClassName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    // Lớp ViewHolder để giữ các View references
    public static class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView tvClassName;
        TextView tvCourseName;
        TextView tvTeacherName;
        // TextView tvDetailLink; // Không cần thiết nếu bạn xử lý click trên toàn bộ item

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần từ item_teacher_class_card.xml
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            // tvDetailLink = itemView.findViewById(R.id.tvDetailLink);
        }
    }


}
