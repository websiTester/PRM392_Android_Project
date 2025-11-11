package com.example.prm392_android_project.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.GroupModel;

import java.util.Locale;

public class GroupAdapter extends ListAdapter<GroupModel, GroupAdapter.GroupViewHolder> {
    public interface OnGroupButtonClickListener {
        void onJoinClick(GroupModel group);
        void onLeaveClick(GroupModel group);
    }

    private final OnGroupButtonClickListener clickListener;
    private Integer currentUserGroupId;

    private static final DiffUtil.ItemCallback<GroupModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<GroupModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull GroupModel oldItem, @NonNull GroupModel newItem) {
                    return oldItem.getGroupId() == newItem.getGroupId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull GroupModel oldItem, @NonNull GroupModel newItem) {
                    return oldItem.getGroupName().equals(newItem.getGroupName()) &&
                            oldItem.getMembers().size() == newItem.getMembers().size();
                }
            };

    public GroupAdapter(OnGroupButtonClickListener listener) {
        super(DIFF_CALLBACK);
        this.clickListener = listener;
        this.currentUserGroupId = null;
    }

    public void setCurrentUserGroupId(Integer groupId) {
        this.currentUserGroupId = groupId;
        // (Chúng ta sẽ dựa vào submitList để refresh thay vì notifyDataSetChanged)
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupName;
        private TextView tvLeader;
        private TextView tvMemberCount;
        private Button btnAction;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tv_group_name);
            tvLeader = itemView.findViewById(R.id.tv_group_leader);
            tvMemberCount = itemView.findViewById(R.id.tv_member_count);
            btnAction = itemView.findViewById(R.id.btn_group_action);
        }

        public void bind(GroupModel group, Integer currentUserGroupId, OnGroupButtonClickListener listener) {
            tvGroupName.setText(group.getGroupName());

            if (group.getLeader() != null) {
                tvLeader.setText("Trưởng nhóm: " + group.getLeader().getFirstName() + " " + group.getLeader().getLastName());
            } else {
                tvLeader.setText("Chưa có trưởng nhóm");
            }

            tvMemberCount.setText(String.format(Locale.getDefault(), "Số thành viên: %d", group.getMembers().size()));

            // --- Logic hiển thị nút ---
            if (currentUserGroupId == null) {
                // User chưa ở trong nhóm nào
                btnAction.setText("Tham gia");
                btnAction.setEnabled(true);
                btnAction.setOnClickListener(v -> listener.onJoinClick(group));

            } else if (currentUserGroupId == group.getGroupId()) {
                // User đang ở trong nhóm này
                btnAction.setText("Rời nhóm");
                btnAction.setEnabled(true);
                btnAction.setOnClickListener(v -> listener.onLeaveClick(group));

            } else {
                // User đang ở trong MỘT NHÓM KHÁC
                btnAction.setText("Đã ở nhóm khác");
                btnAction.setEnabled(false);
                btnAction.setOnClickListener(null);
            }
        }
    }

    // 5. Các hàm của Adapter
    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModel currentGroup = getItem(position);
        holder.bind(currentGroup, currentUserGroupId, clickListener);
    }
}
