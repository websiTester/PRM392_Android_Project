package com.example.prm392_android_project.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_android_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {



    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        view.findViewById(R.id.btnRegister).setOnClickListener(view1 -> {});
        view.findViewById(R.id.txtLink).setOnClickListener(view1 -> {});

        view.findViewById(R.id.txtLink).setOnClickListener(view1 -> {
            LoginFragment loginFragment = new LoginFragment() ;
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).commit();
        });
        return view;
    }
}