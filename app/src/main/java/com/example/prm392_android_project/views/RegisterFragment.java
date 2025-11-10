package com.example.prm392_android_project.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.databinding.FragmentRegisterBinding;
import com.example.prm392_android_project.models.truong.LoginResult;
import com.example.prm392_android_project.models.truong.RegisterModel;
import com.example.prm392_android_project.retrofit.API.LoginAPI;
import com.example.prm392_android_project.retrofit.Client.LoginRetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private Retrofit retrofit;
    private LoginAPI retrofitAPI;
    private Call<ResponseBody> call;

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container,false);
        RegisterModel registerModel = new RegisterModel();
        String role = "Student";
        binding.setRegisterModel(registerModel);
        binding.btnRegister.setOnClickListener(view1 -> {
            if(registerModel.getUsername().length()==0 || registerModel.getPassword().length()==0
            || registerModel.getConfirmPassword().length()==0) {
                Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }


            binding.btnRegister.setEnabled(false);
            if(registerModel.roleId == 0) {
                registerModel.roleId = 2;
            }
            Log.d("REGISTER", registerModel.username);
            Log.d("REGISTER", registerModel.password);
            Log.d("REGISTER", registerModel.confirmPassword);
            Log.d("REGISTER", registerModel.roleId+"");

            retrofit = LoginRetrofitClient.getInstance();
            retrofitAPI = retrofit.create(LoginAPI.class);

            call = retrofitAPI.registerUser(registerModel);
            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            try {
                                String message = responseBody.string();
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                LoginFragment loginFragment = new LoginFragment() ;
                                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).commit();
                            } catch (IOException e) {
                                Toast.makeText(getContext(), "Error reading response", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        try {
                            String errorMessage = response.errorBody().string();
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                    Log.d("LOGIN", "onFailure: " + throwable.getMessage());
                }
            });

            binding.btnRegister.setEnabled(true);
        });

        binding.txtLink.setOnClickListener(view1 -> {
            LoginFragment loginFragment = new LoginFragment() ;
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).commit();
        });

        return binding.getRoot();
    }
}