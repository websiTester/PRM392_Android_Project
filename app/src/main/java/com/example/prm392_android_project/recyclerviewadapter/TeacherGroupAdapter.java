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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.ClassMemberModel;
import com.example.prm392_android_project.models.GroupModel;

import java.util.Locale;

public class TeacherGroupAdapter extends ListAdapter<GroupModel, TeacherGroupAdapter.GroupViewHolder>{
    public interface OnGroupClickListener {
        void onDeleteGroupClick(GroupModel group);
        void onAddMemberClick(GroupModel group);
        void onRemoveMemberClick(GroupModel group, ClassMemberModel member);
    }

    private final OnGroupClickListener clickListener;
    private final LayoutInflater inflater;

    private static final DiffUtil.ItemCallback<GroupModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<GroupModel>() {
                // (Giữ nguyên nội dung)
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

    public TeacherGroupAdapter(Context context, OnGroupClickListener listener) {
        super(DIFF_CALLBACK);
        this.clickListener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupName;
        private TextView tvLeader;
        private TextView tvMemberCount;
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
            llMembersContainer = itemView.findViewById(R.id.ll_members_container); // <-- Ánh xạ
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
        GroupModel currentGroup = getItem(position);
        holder.bind(currentGroup, clickListener, inflater);
    }
}
