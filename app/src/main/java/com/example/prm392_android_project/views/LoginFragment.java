package com.example.prm392_android_project.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.databinding.FragmentLoginBinding;
import com.example.prm392_android_project.models.LoginModel;
import com.example.prm392_android_project.models.truong.LoginResult;
import com.example.prm392_android_project.retrofit.API.LoginAPI;
import com.example.prm392_android_project.retrofit.Client.LoginRetrofitClient;
import com.example.prm392_android_project.viewmodels.LoginViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginFragment extends Fragment {

    private Retrofit retrofit;
    private LoginAPI retrofitAPI;
    private Call<LoginResult> call;
    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

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
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(view1 -> {
            //Toast.makeText(binding.getRoot().getContext(), loginModel.getUsername() +" - "+loginModel.getPassowrd(), Toast.LENGTH_SHORT).show();

            if(loginModel.getUsername().length()==0 || loginModel.getPassowrd().length()==0) {
                Toast.makeText(getContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            retrofit = LoginRetrofitClient.getInstance();
            retrofitAPI = retrofit.create(LoginAPI.class);

            call = retrofitAPI.login(loginModel);
            call.enqueue(new Callback<LoginResult>() {

                @Override
                public void onResponse(@NonNull Call<LoginResult> call, @NonNull Response<LoginResult> response) {
                    Log.d("LOGIN", "Getting User.....");
                    Log.d("LOGIN", response.toString());
                    if (response.isSuccessful()) {
                        LoginResult result = response.body();
                        loginViewModel.setLoginResult(result);
                        HomeFragment homeFragment = new HomeFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                        Log.d("LOGIN", result.getUsername());
                    } else {
                        Toast.makeText(getContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("LOGIN", "Get User Successful");
                }

                @Override
                public void onFailure(@NonNull Call<LoginResult> call, @NonNull Throwable throwable) {
                    Log.d("LOGIN", "onFailure: " + throwable.getMessage());
                }
            });


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