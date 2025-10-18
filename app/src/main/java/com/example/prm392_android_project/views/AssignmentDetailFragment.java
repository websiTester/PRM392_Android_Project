package com.example.prm392_android_project.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.recyclerviewadapter.TaskRecyclerViewAdapter;
import com.example.prm392_android_project.viewmodels.AssignmentDetailViewModel;

public class AssignmentDetailFragment extends Fragment {

    private RecyclerView todoRecyclerView;
    private  RecyclerView doingRecyclerView;
    private  RecyclerView doneRecyclerView;
    private TaskRecyclerViewAdapter adapter;
    private AssignmentDetailViewModel viewModel;



    public AssignmentDetailFragment() {
        // Required empty public constructor
    }

    public static AssignmentDetailFragment newInstance(String param1, String param2) {
        return new AssignmentDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AssignmentDetailViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_detail, container, false);

        todoRecyclerView = view.findViewById(R.id.todo_recycler_view);
        doingRecyclerView = view.findViewById(R.id.doing_recycler_view);
        doneRecyclerView = view.findViewById(R.id.done_recycler_view);


        viewModel.getArticleLiveData().observe(getViewLifecycleOwner(), groupTasks -> {
                    adapter = new TaskRecyclerViewAdapter(groupTasks);
                    todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    todoRecyclerView.setAdapter(adapter);
                }
        );

        return view ;
    }

}