package com.example.prm392_android_project.views;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
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
import com.example.prm392_android_project.utils.Validatior;

import java.io.ByteArrayOutputStream;


public class ProfileFragment extends Fragment implements View.OnClickListener{


    private Button btnCancel, btnDone;
    private ImageView imgEditAvarta;
    private EditText edtName, edtEmail, edtDob;
    private RadioGroup radioGroupGender;
    private RadioButton rbGender;
    private String imgBase64String = "";

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
        edtName = view.findViewById(R.id.edtName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtDob = view.findViewById(R.id.edtDob);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
    }

    public void getDataFromSharedPrefrence(){
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String dob = sharedPreferences.getString("dob", "");
        String gender = sharedPreferences.getString("gender", "");
        imgBase64String = sharedPreferences.getString("avarta", "");
        edtName.setText(name);
        edtEmail.setText(email);
        edtDob.setText(dob);

        if(gender.equals("female")){
            radioGroupGender.check(R.id.rbFemale);
        } else {
            radioGroupGender.check(R.id.rbMale);
        }

        if (imgBase64String.equals("")){
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

        Validatior validatior = new Validatior();
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String dob = edtDob.getText().toString();
        String gender = "";
        if (radioGroupGender.getCheckedRadioButtonId() == R.id.rbMale){
            gender = "male";
        } else  gender = "female";

        if(name.equals("") ||  email.equals("")
                || dob.equals("")){
            Toast.makeText(view.getContext(), "Please fill all the field", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(validatior.validateDob(dob)==false){
                Toast.makeText(view.getContext(), "Date of birth format yyyy/MM/dd", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("email", email);
                editor.putString("dob", dob);
                editor.putString("gender", gender);
                editor.putString("avarta", imgBase64String);
                editor.apply();
                Toast.makeText(view.getContext(), "Update successful", Toast.LENGTH_SHORT).show();
            }
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
    }
}