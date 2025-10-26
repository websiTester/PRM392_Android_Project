package com.example.prm392_android_project.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.prm392_android_project.models.truong.LoginResult;

public class LoginViewModel extends ViewModel {
    private LoginResult loginResult;

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }
}
