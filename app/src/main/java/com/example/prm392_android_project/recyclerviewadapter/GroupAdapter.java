package com.example.prm392_android_project.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.GroupModel;

import java.util.List;
import java.util.Locale;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    public interface OnGroupButtonClickListener {
        void onJoinClick(GroupModel group);
        void onLeaveClick(GroupModel group);
    }

    private final List<GroupModel> groupList;
    private final OnGroupButtonClickListener clickListener;
    private Integer currentUserGroupId;

    public GroupAdapter(List<GroupModel> groupList, OnGroupButtonClickListener listener) {
        this.groupList = groupList;
        this.clickListener = listener;
        this.currentUserGroupId = null;
    }


    public void setCurrentUserGroupId(Integer groupId) {
        this.currentUserGroupId = groupId;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupName, tvLeader, tvMemberCount;
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

            if (currentUserGroupId == null) {
                btnAction.setText("Tham gia");
                btnAction.setEnabled(true);
                btnAction.setOnClickListener(v -> listener.onJoinClick(group));
            } else if (currentUserGroupId == group.getGroupId()) {
                btnAction.setText("Rời nhóm");
                btnAction.setEnabled(true);
                btnAction.setOnClickListener(v -> listener.onLeaveClick(group));
            } else {
                btnAction.setText("Đã ở nhóm khác");
                btnAction.setEnabled(false);
                btnAction.setOnClickListener(null);
            }
        }
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModel currentGroup = groupList.get(position); // SỬA: Lấy từ List
        holder.bind(currentGroup, currentUserGroupId, clickListener);
    }

    @Override
    public int getItemCount() {
        return groupList != null ? groupList.size() : 0; // SỬA: Đếm từ List
    }
}