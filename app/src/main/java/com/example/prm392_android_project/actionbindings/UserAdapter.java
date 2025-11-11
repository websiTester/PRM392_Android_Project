package com.example.prm392_android_project.actionbindings;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.UserItem;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public interface OnUserClickListener {
        void onUserClick(UserItem user);
    }

    private final List<UserItem> items = new ArrayList<>();
    private final OnUserClickListener listener;

    public UserAdapter(OnUserClickListener listener) {
        this.listener = listener;
    }

    // Cập nhật toàn bộ danh sách
    public void setData(List<UserItem> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    // Cập nhật 1 user (sau khi toggle active)
    public void updateItem(UserItem updated) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getUserId() == updated.getUserId()) {
                items.set(i, updated);
                notifyItemChanged(i);
                return;
            }
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserItem user = items.get(position);

        String fullName = user.getFullName();
        if (fullName == null) fullName = "";
        fullName = fullName.trim();

        String email = user.getEmail();
        String role  = user.getRoleName();

        holder.tvName.setText(fullName);
        holder.tvEmail.setText(email == null ? "" : email);
        holder.tvRole.setText(role == null ? "" : role);

        // Avatar: 1–2 chữ cái, an toàn
        holder.tvAvatar.setText(getInitials(fullName));

        // Màu role
        if (role != null && role.contains("Giảng viên")) {
            holder.tvRole.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            holder.tvRole.setTextColor(Color.parseColor("#3F51B5"));
        }

        // Hiệu ứng theo active
        holder.itemView.setAlpha(user.isActive() ? 1.0f : 0.5f);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(user);
            }
        });

        Log.d("DBG", "onBindViewHolder position=" + position + ", name=" + fullName);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Lấy chữ cái avatar một cách an toàn, không bao giờ substring chuỗi rỗng
    private String getInitials(String name) {
        if (name == null) return "?";
        name = name.trim();
        if (name.isEmpty()) return "?";

        String[] parts = name.split("\\s+");
        if (parts.length == 0) return "?";

        // Ví dụ: "Nguyễn Văn A" -> "VA"
        if (parts.length == 1) {
            return String.valueOf(Character.toUpperCase(parts[0].charAt(0)));
        } else {
            String a = parts[parts.length - 2];
            String b = parts[parts.length - 1];
            char c1 = Character.toUpperCase(a.charAt(0));
            char c2 = Character.toUpperCase(b.charAt(0));
            return "" + c1 + c2;
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvAvatar, tvName, tvEmail, tvRole;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
            tvName   = itemView.findViewById(R.id.tvName);
            tvEmail  = itemView.findViewById(R.id.tvEmail);
            tvRole   = itemView.findViewById(R.id.tvRole);
        }
    }
}
