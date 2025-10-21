package com.example.prm392_android_project.views;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_android_project.R;

public class TruongTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_truong_test);

        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).commit();
    }
}