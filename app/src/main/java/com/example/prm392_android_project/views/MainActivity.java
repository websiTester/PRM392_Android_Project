package com.example.prm392_android_project.views;

import static android.view.View.GONE;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_android_project.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test_assignment_detail).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.test_assignment_detail) {
            view.findViewById(R.id.test_assignment_detail).setVisibility(GONE);

            AssignmentDetailFragment fragment = new AssignmentDetailFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}