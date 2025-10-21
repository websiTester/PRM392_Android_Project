package com.example.prm392_android_project.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_android_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.btnLogin).setOnClickListener(view1 -> {});
        view.findViewById(R.id.txtLink).setOnClickListener(view1 -> {});
        view.findViewById(R.id.btnRegister).setOnClickListener(view1 -> {
            RegisterFragment registerFragment = new RegisterFragment() ;
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment).commit();
        });
        return view;
    }
}