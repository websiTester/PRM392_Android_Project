package com.example.prm392_android_project.actionbindings;

import com.example.prm392_android_project.viewmodels.AssignmentDetailViewModel;


public class ChangeTaskColumnEvent  {
private final AssignmentDetailViewModel assignmentDetailViewModel;

    public ChangeTaskColumnEvent(AssignmentDetailViewModel assignmentDetailViewModel) {
        this.assignmentDetailViewModel = assignmentDetailViewModel;
    }

    public void OnChangTaskStatus(int id){
        assignmentDetailViewModel.ChangeTaskStatus(id);
    }

}
