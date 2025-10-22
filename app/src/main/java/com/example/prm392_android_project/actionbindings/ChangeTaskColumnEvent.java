package com.example.prm392_android_project.actionbindings;

import android.util.Log;

import com.example.prm392_android_project.viewmodels.AssignmentDetailViewModel;


public class ChangeTaskColumnEvent  {
private final AssignmentDetailViewModel assignmentDetailViewModel;

    public ChangeTaskColumnEvent(AssignmentDetailViewModel assignmentDetailViewModel) {
        this.assignmentDetailViewModel = assignmentDetailViewModel;
    }

    public void OnChangeTaskStatus(int id){
        Log.d("ChangeTaskColumnEvent", "OnChangeTaskStatus: "+id);
        assignmentDetailViewModel.ChangeTaskStatus(id);
    }

}
