package com.example.prm392_android_project.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.GroupGradeModel;
import com.example.prm392_android_project.models.GroupModel;

import java.util.List;

public class GroupSelectionAdapter extends RecyclerView.Adapter<GroupSelectionAdapter.ViewHolder> {

    public static class GroupWithGrade {
        public GroupModel group;
        public String gradeDisplay;

        public GroupWithGrade(GroupModel group, String gradeDisplay) {
            this.group = group;
            this.gradeDisplay = gradeDisplay;
        }
    }

    public interface OnGroupSelectListener {
        void onGroupSelected(GroupModel group);
    }

    private final List<GroupWithGrade> items;
    private final OnGroupSelectListener listener;

    public GroupSelectionAdapter(List<GroupWithGrade> items, OnGroupSelectListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dialog_select_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupWithGrade item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGroupName, tvGroupGrade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tv_dialog_group_name);
            tvGroupGrade = itemView.findViewById(R.id.tv_dialog_group_grade);
        }

        public void bind(GroupWithGrade item, OnGroupSelectListener listener) {
            tvGroupName.setText(item.group.getGroupName());
            tvGroupGrade.setText(item.gradeDisplay);

            itemView.setOnClickListener(v -> listener.onGroupSelected(item.group));
        }
    }
}