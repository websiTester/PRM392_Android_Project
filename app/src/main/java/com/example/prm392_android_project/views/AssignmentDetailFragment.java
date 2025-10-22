package com.example.prm392_android_project.views;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.actionbindings.ChangeTaskColumnEvent;
import com.example.prm392_android_project.models.GroupTask;
import com.example.prm392_android_project.recyclerviewadapter.TaskRecyclerViewAdapter;
import com.example.prm392_android_project.viewmodels.AssignmentDetailViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

public class AssignmentDetailFragment extends Fragment {

    private RecyclerView todoRecyclerView;
    private RecyclerView doingRecyclerView;
    private RecyclerView doneRecyclerView;
    private TaskRecyclerViewAdapter todoAdapter;
    private TaskRecyclerViewAdapter doingAdapter;
    private TaskRecyclerViewAdapter doneAdapter;
    private AssignmentDetailViewModel viewModel;
    private PieChart chart;
    private static int groupId;
    private static int assignmentId;




    public AssignmentDetailFragment() {
        // Required empty public constructor
    }

    public static AssignmentDetailFragment newInstance(String param1, String param2) {

        groupId = Integer.parseInt(param1);
        assignmentId = Integer.parseInt(param2);
        return new AssignmentDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(AssignmentDetailViewModel.class);
        viewModel.initialSetGroupIdAndAssignmentIdToaddingGroupTask(groupId,assignmentId);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_detail, container, false);

        chart = view.findViewById(R.id.chart1);

        todoRecyclerView = view.findViewById(R.id.todo_recycler_view);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        doingRecyclerView = view.findViewById(R.id.doing_recycler_view);
        doingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        doneRecyclerView = view.findViewById(R.id.done_recycler_view);
        doneRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        todoAdapter = new TaskRecyclerViewAdapter();
        todoAdapter.setChangeTaskColumnEvent(new ChangeTaskColumnEvent(viewModel));
        doingAdapter = new TaskRecyclerViewAdapter();
        doingAdapter.setChangeTaskColumnEvent(new ChangeTaskColumnEvent(viewModel));
        doneAdapter = new TaskRecyclerViewAdapter();
        doneAdapter.setChangeTaskColumnEvent(new ChangeTaskColumnEvent(viewModel));


        todoRecyclerView.setAdapter(todoAdapter);
        doingRecyclerView.setAdapter(doingAdapter);
        doneRecyclerView.setAdapter(doneAdapter);

        viewModel.getTodoGroupTaskLiveData().observe(getViewLifecycleOwner(), groupTasks -> {
            Log.d("AssignmentDetailFragment", "Todo group tasks change");
            List<GroupTask> todoGroupTasks = new ArrayList<>(groupTasks);
            todoAdapter.submitList(todoGroupTasks);

        });

        viewModel.getInProgressGroupTaskLiveData().observe(getViewLifecycleOwner(), groupTasks -> {
            Log.d("AssignmentDetailFragment", "Doing group tasks change");
            List<GroupTask> inProgressGroupTasks = new ArrayList<>(groupTasks);
            doingAdapter.submitList(inProgressGroupTasks);
        });

        viewModel.getDoneGroupTaskLiveData().observe(getViewLifecycleOwner(), groupTasks -> {
            Log.d("AssignmentDetailFragment", "Done group tasks change");
            List<GroupTask> doneGroupTasks = new ArrayList<>(groupTasks);
            doneAdapter.submitList(doneGroupTasks);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.add_task_btn).setOnClickListener(view1 -> {
            CreateTaskDialogFragment dialog = CreateTaskDialogFragment.newInstance();
            dialog.show(getParentFragmentManager(), CreateTaskDialogFragment.TAG);
        });
        setData();
    }

    private void setData() {
        IMarker marker = new CustomPieChartMarker(getContext(), R.layout.custom_marker_view);
        chart.setMarker(marker);

        chart.setHighlightPerTapEnabled(true);
        ArrayList<PieEntry> entries = new ArrayList<>();

        float toDoValue = 5f;
        float doingValue = 4f;
        float doneValue = 2f;

        entries.add(new PieEntry(toDoValue, "Todo"));
        entries.add(new PieEntry(doneValue, "Doing"));
        entries.add(new PieEntry(doingValue, "Done"));

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f); // Tắt hiệu ứng nhấn vào
        dataSet.setDrawValues(false);
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);

        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }
}