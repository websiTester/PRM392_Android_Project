package com.example.prm392_android_project.views;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.databinding.FragmentAddPeerReviewBinding;
import com.example.prm392_android_project.dtos.GetUserForDropdownResponse;
import com.example.prm392_android_project.retrofit.API.UserAPI;
import com.example.prm392_android_project.retrofit.Client.RetrofitClient;
import com.example.prm392_android_project.viewmodels.PeerReviewViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AddPeerReviewFragment extends DialogFragment {
    public static final String TAG = "AddPeerReviewFragment";
    private final Retrofit retrofit = RetrofitClient.getInstance();
    private final UserAPI retrofitAPI = retrofit.create(UserAPI.class);
    private static int groupId;
    private static int assignmentId;
    private int currentReviewerId;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private FragmentAddPeerReviewBinding binding;
    private PeerReviewViewModel viewModel;




    public AddPeerReviewFragment() {
        // Required empty public constructor
    }
    public static AddPeerReviewFragment newInstance(int param1, int param2) {
        groupId = param1;
        assignmentId = param2;
        return new AddPeerReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PeerReviewViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_add_peer_review,
                container,
                false
        );
        viewModel.getPeerReview().observe(getViewLifecycleOwner(), peerReview -> {
            if (peerReview != null) {
                binding.setPeerReview(peerReview);
            }
        });
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = binding.spinnerMember;
        getUserForDropdown(spinner);
        binding.btnAddReview.setOnClickListener(v -> {
            viewModel.addPeerReview(currentReviewerId,2,assignmentId,groupId);
            dismiss();
        });

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        binding = null;
    }
    private void getUserForDropdown(Spinner spinner){
        Disposable disposable = retrofitAPI.getUsersForDropdown(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        users -> {
                            if (users != null) {
                                GetUserForDropdownResponse currentUsingUser = users.stream()
                                        .filter(user -> user.getUserId() == 2).findFirst().orElse(null);
                                users.remove(currentUsingUser);
                                ArrayAdapter<GetUserForDropdownResponse> adapter = new ArrayAdapter<>(
                                        getContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        users
                                );
                                spinner.setAdapter(adapter);
                                binding.spinnerMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        GetUserForDropdownResponse selectedUser = (GetUserForDropdownResponse) parent.getItemAtPosition(position);
                                        if (selectedUser != null) {
                                            int reviewerId = selectedUser.getUserId();
                                            currentReviewerId = reviewerId;
                                            viewModel.fetchPeerReview(reviewerId, 2, assignmentId, groupId);
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                                Log.d(TAG, "Fetched " + users.size() + " users successfully.");
                            } else {
                                Log.d(TAG, "Fetched 0 users or list is null.");
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "Error fetching articles: " + throwable.getMessage());
                        }
                );
        mCompositeDisposable.add(disposable);
    }
}