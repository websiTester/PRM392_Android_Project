package com.example.prm392_android_project.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.databinding.FragmentLoginBinding;
import com.example.prm392_android_project.models.LoginModel;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);
        LoginModel loginModel = new LoginModel();


        binding.btnLogin.setOnClickListener(view1 -> {
            //Toast.makeText(binding.getRoot().getContext(), loginModel.getUsername() +" - "+loginModel.getPassowrd(), Toast.LENGTH_SHORT).show();




        });

        binding.txtLink.setOnClickListener(view1 -> {});
        binding.btnRegister.setOnClickListener(view1 -> {
            RegisterFragment registerFragment = new RegisterFragment() ;
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment).commit();
        });
        binding.setLoginModel(loginModel);



        return binding.getRoot();
    }
}