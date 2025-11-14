package com.example.prm392_android_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.truong.LoginResult;

public class LoginSuccessFragment extends Fragment {

    private LoginResult user;

    public LoginSuccessFragment(LoginResult user) {
        // Required empty public constructor
        this.user = user;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_success, container, false);
        view.findViewById(R.id.btnStartNow1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginSuccess", "Onclick");
                if(user.getRoleId()==2) {
                    Log.d("LoginSuccess", "Teacher");
                    Intent intent = new Intent(view.getContext(), TeacherClassActivity.class);
                    startActivity(intent);
                }
                else if(user.getRoleId()==1)
                {
                    Log.d("LoginSuccess", "Student");
                    Intent intent=new Intent(view.getContext(), StudentClassActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Log.d("LoginSuccess", "Admin");
                    Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}