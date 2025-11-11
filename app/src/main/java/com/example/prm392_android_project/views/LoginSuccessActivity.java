package com.example.prm392_android_project.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.DataCallBackFromFragment;
import com.example.prm392_android_project.models.truong.LoginResult;
import com.example.prm392_android_project.viewmodels.LoginViewModel;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class LoginSuccessActivity extends AppCompatActivity  implements DataCallBackFromFragment {

    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int GALLERY_PERMISSION_CODE = 101;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View navHeader;
    private ProfileFragment profileFragment;
    private LoginResult user;
    private LoginViewModel loginViewModel;

    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            activityResultCallback -> {
                if (activityResultCallback.getData() != null) {

                    Uri selectedImageUri = activityResultCallback.getData().getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                        profileFragment.setImageAvarta(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            activityResultCallback -> {
                if (activityResultCallback.getData() != null) {

                    Bitmap bitmap = (Bitmap) activityResultCallback.getData().getExtras().get("data");
                    profileFragment.setImageAvarta(bitmap);

                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        user = (LoginResult) getIntent().getSerializableExtra("user");
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        SharedPreferences pref = this.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("userId", user.getId());
        editor.apply();

        loginViewModel.setLoginResult(user);
        Log.d("LoginSuccessActivity", user.getUsername()+"");
        Log.d("LoginSuccessActivity", user.getFirstname()+"");
        Log.d("LoginSuccessActivity", user.getLastname()+"");
        Log.d("LoginSuccessActivity", user.getAvarta()+"");
        profileFragment = new ProfileFragment(this, this);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

//        //Set Menu Button
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");

        navHeader = navigationView.getHeaderView(0);

        setNavHeader();
        Button editButton = navHeader.findViewById(R.id.btnEdit);
        editButton.setOnClickListener(view -> {
            replaceProfileFragment();
        });

        Button logoutBtn = navHeader.findViewById(R.id.btnLogout);
        logoutBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TruongTestActivity.class);
            startActivity(intent);

        });

        Intent intent = new Intent(this, TeacherClassActivity.class);
        startActivity(intent);


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;
    }
    @Override
    public void setNavHeader(){
        TextView txtName = navHeader.findViewById(R.id.txtName);
        TextView txtUsername = navHeader.findViewById(R.id.txtUsername);
        TextView txtEmail = navHeader.findViewById(R.id.txtEmail);
        ImageView imgAvarta = navHeader.findViewById(R.id.imgAvarta);

        txtName.setText(loginViewModel.getLoginResult().getLastname());
        txtEmail.setText(loginViewModel.getLoginResult().getEmail());
        txtUsername.setText(loginViewModel.getLoginResult().getUsername());
        String imgBase64 = loginViewModel.getLoginResult().getAvarta();

        if("".equals(imgBase64) || imgBase64 == null){
            imgAvarta.setImageResource(R.mipmap.ic_launcher);
        } else {
            Bitmap bitmap = decodeBase64String(imgBase64);
            imgAvarta.setImageBitmap(bitmap);
        }
        getSupportFragmentManager().popBackStack();
        drawerLayout.openDrawer(GravityCompat.START);



    }

    public Bitmap decodeBase64String(String base64String){
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    @Override
    public void backToPreviousScreen() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(LoginSuccessActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(LoginSuccessActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(LoginSuccessActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            if (requestCode == GALLERY_PERMISSION_CODE) {
                selectImage();
            } else if(requestCode == CAMERA_PERMISSION_CODE){
                openCamera();
            }
        }
    }


    public void replaceProfileFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, profileFragment)
                .addToBackStack(null).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    public void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }


    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }
}