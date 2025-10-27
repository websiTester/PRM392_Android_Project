package com.example.prm392_android_project.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.DataCallBackFromFragment;
import com.example.prm392_android_project.models.LoginModel;
import com.example.prm392_android_project.models.truong.LoginResult;
import com.example.prm392_android_project.retrofit.API.LoginAPI;
import com.example.prm392_android_project.retrofit.Client.LoginRetrofitClient;
import com.example.prm392_android_project.utils.Validatior;
import com.example.prm392_android_project.viewmodels.LoginViewModel;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProfileFragment extends Fragment implements View.OnClickListener{


    private Retrofit retrofit;
    private LoginAPI retrofitAPI;
    private Call<LoginResult> call;
    private Button btnCancel, btnDone;
    private ImageView imgEditAvarta;
    private EditText edtFirstName, edtLastName, edtEmail, edtUsername;
    private RadioGroup radioGroupGender;
    private RadioButton rbGender;
    private String imgBase64String = "";
    private LoginViewModel loginViewModel;


    private SharedPreferences sharedPreferences;
    private Context context;
    private DataCallBackFromFragment dataCallBackFromFragment;

    public ProfileFragment(DataCallBackFromFragment dataCallBackFromFragment, Context context) {
        this.dataCallBackFromFragment = dataCallBackFromFragment;
        sharedPreferences = context.getSharedPreferences("profile_pref", Context.MODE_PRIVATE);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        Log.d("ProfileFragment", loginViewModel.getLoginResult().getUsername());
        declareVariables(view);
        getDataFromSharedPrefrence();

        imgEditAvarta.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        return view;
    }

    public void declareVariables(View view){
        imgEditAvarta = view.findViewById(R.id.imgEditAvarta);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnDone = view.findViewById(R.id.btnDone);
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtEmail = view.findViewById(R.id.edtEmail);


    }

    public void getDataFromSharedPrefrence(){
//        String name = sharedPreferences.getString("name", "");
//        String email = sharedPreferences.getString("email", "");
//        String dob = sharedPreferences.getString("dob", "");
//        String gender = sharedPreferences.getString("gender", "");
//        imgBase64String = sharedPreferences.getString("avarta", "");

        String firstName = loginViewModel.getLoginResult().getFirstname();
        String lastName = loginViewModel.getLoginResult().getLastname();
        String username = loginViewModel.getLoginResult().getUsername();
        String email = loginViewModel.getLoginResult().getEmail();
        imgBase64String = loginViewModel.getLoginResult().getAvarta();
        edtFirstName.setText(firstName);
        edtLastName.setText(lastName);
        edtFirstName.setText(firstName);
        edtUsername.setText(username);
        edtEmail.setText(email);

        if ("".equals(imgBase64String) || imgBase64String==null){
            imgEditAvarta.setImageResource(R.mipmap.ic_launcher);
        } else {
            Bitmap bitmap = decodeBase64String(imgBase64String);
            imgEditAvarta.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btnCancel){
            dataCallBackFromFragment.backToPreviousScreen();
        } else if(id == R.id.btnDone){

            if(validateInput(view)){
                dataCallBackFromFragment.setNavHeader();
            }
        } else if(id == R.id.imgEditAvarta){
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    if(id==R.id.camera){
                        dataCallBackFromFragment.checkPermission(Manifest.permission.CAMERA, LoginSuccessActivity.CAMERA_PERMISSION_CODE);
                    } else {
                        dataCallBackFromFragment.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, LoginSuccessActivity.GALLERY_PERMISSION_CODE);
                    }

                    return true;
                }
            });

            popupMenu.show();
        }
    }





    public boolean validateInput(View view){


        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String username = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();


        if(firstName.equals("") ||  email.equals("")
                || lastName.equals("") || username.equals("") ){
            Toast.makeText(view.getContext(), "Please fill all the field", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            retrofit = LoginRetrofitClient.getInstance();
            retrofitAPI = retrofit.create(LoginAPI.class);
            LoginResult loginResult = new LoginResult(loginViewModel.getLoginResult().getId(),
                   username,email,loginViewModel.getLoginResult().getAvarta() ,firstName,lastName);
            call = retrofitAPI.updateUser(loginResult);
            call.enqueue(new Callback<LoginResult>() {

                @Override
                public void onResponse(@NonNull Call<LoginResult> call, @NonNull Response<LoginResult> response) {
                    Log.d("LOGIN", "Getting User.....");
                    Log.d("ProfileFragment", response.toString());
                    if (response.isSuccessful()) {
                        LoginResult result = response.body();
                        loginViewModel.setLoginResult(result);

                        Log.d("ProfileFragment", result.getUsername());
                    } else {
                        Toast.makeText(getActivity(), "Can not update profile", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("LOGIN", "Get User Successful");
                }

                @Override
                public void onFailure(@NonNull Call<LoginResult> call, @NonNull Throwable throwable) {
                    Log.d("LOGIN", "onFailure: " + throwable.getMessage());
                }
            });

            Toast.makeText(view.getContext(), "Update successful", Toast.LENGTH_SHORT).show();

        }

        return true;
    }


    public String converImageToBase64(Uri uri){
        String base64 = "";
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] bytes = stream.toByteArray();
            base64 = Base64.encodeToString(bytes, Base64.DEFAULT);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return base64;
    }

    public String converImageToBase64(Bitmap bitmap){
        String base64 = "";
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] bytes = stream.toByteArray();
            base64 = Base64.encodeToString(bytes, Base64.DEFAULT);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return base64;
    }


    public Bitmap decodeBase64String(String base64String){
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }


    public void setImageAvarta(Bitmap bitmap){
        imgEditAvarta.setImageBitmap(bitmap);
        imgBase64String = converImageToBase64(bitmap);
        loginViewModel.getLoginResult().setAvarta(imgBase64String);
    }
}