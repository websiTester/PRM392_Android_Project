package com.example.prm392_android_project.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.databinding.TaskLayoutBinding;
import com.example.prm392_android_project.models.GroupTask;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TodoViewHolder>{

    private List<GroupTask> tasks;

    public TaskRecyclerViewAdapter(List<GroupTask> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TaskLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.task_layout, parent, false);
        return new TodoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        GroupTask task = tasks.get(position);
        holder.binding.setTask(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
    public static class TodoViewHolder extends  RecyclerView.ViewHolder {
        private final TaskLayoutBinding binding;
        public TodoViewHolder(@NonNull TaskLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
