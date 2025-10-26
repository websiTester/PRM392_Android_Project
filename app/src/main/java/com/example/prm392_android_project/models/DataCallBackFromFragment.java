package com.example.prm392_android_project.models;

public interface DataCallBackFromFragment {
    void backToPreviousScreen();

    void checkPermission(String permission, int requestCode);

    void setNavHeader();
}
