package com.example.prm392_android_project.recyclerviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.ClassMemberModel;
import com.example.prm392_android_project.models.GroupModel;

import java.util.List;
import java.util.Locale;

public class TeacherGroupAdapter extends RecyclerView.Adapter<TeacherGroupAdapter.GroupViewHolder>{

    public interface OnGroupClickListener {
        void onDeleteGroupClick(GroupModel group);
        void onAddMemberClick(GroupModel group);
        void onRemoveMemberClick(GroupModel group, ClassMemberModel member);
    }

    private final List<GroupModel> groupList;
    private final OnGroupClickListener clickListener;
    private final LayoutInflater inflater;

    public TeacherGroupAdapter(Context context, List<GroupModel> list, OnGroupClickListener listener) {
        this.groupList = list;
        this.clickListener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupName, tvLeader, tvMemberCount;
        private ImageButton btnDeleteGroup;
        private Button btnAddMember;
        private LinearLayout llMembersContainer;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tv_group_name);
            tvLeader = itemView.findViewById(R.id.tv_group_leader);
            tvMemberCount = itemView.findViewById(R.id.tv_member_count);
            btnDeleteGroup = itemView.findViewById(R.id.btn_delete_group);
            btnAddMember = itemView.findViewById(R.id.btn_add_member);
            llMembersContainer = itemView.findViewById(R.id.ll_members_container);
        }

        public void bind(GroupModel group, OnGroupClickListener listener, LayoutInflater inflater) {
            tvGroupName.setText(group.getGroupName());

            if (group.getLeader() != null) {
                tvLeader.setText("Trưởng nhóm: " + group.getLeader().getFirstName() + " " + group.getLeader().getLastName());
            } else {
                tvLeader.setText("Chưa có trưởng nhóm");
            }

            tvMemberCount.setText(String.format(Locale.getDefault(), "Số thành viên: %d", group.getMembers().size()));

            btnDeleteGroup.setOnClickListener(v -> listener.onDeleteGroupClick(group));
            btnAddMember.setOnClickListener(v -> listener.onAddMemberClick(group));

            llMembersContainer.removeAllViews();

            for (ClassMemberModel member : group.getMembers()) {
                View memberView = inflater.inflate(R.layout.item_group_member_teacher, llMembersContainer, false);
                TextView tvMemberName = memberView.findViewById(R.id.tv_member_name);
                ImageButton btnRemoveMember = memberView.findViewById(R.id.btn_remove_member);
                tvMemberName.setText(member.getFirstName() + " " + member.getLastName());
                btnRemoveMember.setOnClickListener(v -> {
                    listener.onRemoveMemberClick(group, member);
                });
                llMembersContainer.addView(memberView);
            }
        }
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_teacher, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModel currentGroup = groupList.get(position);
        holder.bind(currentGroup, clickListener, inflater);
    }

    @Override
    public int getItemCount() {
        return groupList != null ? groupList.size() : 0;
    }
}