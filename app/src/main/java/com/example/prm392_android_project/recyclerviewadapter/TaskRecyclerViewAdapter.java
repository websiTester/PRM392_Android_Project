package com.example.prm392_android_project.recyclerviewadapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.actionbindings.ChangeTaskColumnEvent;
import com.example.prm392_android_project.databinding.TaskLayoutBinding;
import com.example.prm392_android_project.models.GroupTask;
import com.example.prm392_android_project.viewmodels.AssignmentDetailViewModel;

import java.util.List;

public class TaskRecyclerViewAdapter extends ListAdapter<GroupTask, TaskRecyclerViewAdapter.TodoViewHolder> {

    private  ChangeTaskColumnEvent changeTaskColumnEvent;
    public TaskRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setChangeTaskColumnEvent(ChangeTaskColumnEvent changeTaskColumnEvent) {
        this.changeTaskColumnEvent = changeTaskColumnEvent;
    }

    private static final DiffUtil.ItemCallback<GroupTask> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull GroupTask oldItem, @NonNull GroupTask newItem) {
            return oldItem.getTaskId() == newItem.getTaskId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull GroupTask oldItem, @NonNull GroupTask newItem) {
            return oldItem.equals(newItem);
        }
    };
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TaskLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.task_layout, parent, false);
        return new TodoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        GroupTask task = getTaskInAdapter(position);
        holder.binding.setHandleEvent(changeTaskColumnEvent);
        holder.binding.setTask(task);
    }

    public GroupTask getTaskInAdapter(int position) {
        return getItem(position);
    }

    public static class TodoViewHolder extends  RecyclerView.ViewHolder {
        private final TaskLayoutBinding binding;
        public TodoViewHolder(@NonNull TaskLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
