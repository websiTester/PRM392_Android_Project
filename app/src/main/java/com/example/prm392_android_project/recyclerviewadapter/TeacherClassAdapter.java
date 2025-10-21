package com.example.prm392_android_project.recyclerviewadapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.ClassItem;

import java.util.List;

public class TeacherClassAdapter extends RecyclerView.Adapter<TeacherClassAdapter.ViewHolder> {

    private List<ClassItem> classList;

    public TeacherClassAdapter(List<ClassItem> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teacher_class_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassItem item = classList.get(position);
        holder.tvClassName.setText(item.getClassName() + " (" + item.getClassCode() + ")");
        holder.tvCourseName.setText("Khóa học: " + item.getCourseName());
        holder.tvTeacherName.setText("GV: " + item.getTeacherFullName());
        holder.tvClassCode.setText(""+item.getClassCode());

        String classCode= item.getClassCode();

        holder.ibCopyClassCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Lấy Context từ item view
                Context context = holder.itemView.getContext();

                // 2. Lấy dịch vụ Clipboard
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

                // 3. Tạo ClipData (dữ liệu để sao chép)
                // "ClassCode" là nhãn, classCode là nội dung
                ClipData clip = ClipData.newPlainText("ClassCode", classCode);

                // 4. Đặt dữ liệu vào clipboard
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                }

                // 5. (Tùy chọn) Hiển thị thông báo xác nhận
                Toast.makeText(context, "Đã sao chép mã lớp: " + classCode, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return classList != null ? classList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName, tvCourseName, tvTeacherName, tvDetailLink,tvClassCode;
        ImageButton ibCopyClassCode;
        ViewHolder(View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvDetailLink = itemView.findViewById(R.id.tvDetailLink);
            tvClassCode = itemView.findViewById(R.id.tvClassCode);
            ibCopyClassCode = itemView.findViewById(R.id.ibCopyClassCode);
        }
    }
}