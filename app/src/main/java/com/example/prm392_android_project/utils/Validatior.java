package com.example.prm392_android_project.utils;

import java.text.SimpleDateFormat;

public class Validatior {
    public boolean validateDob(String dob){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            sdf.parse(dob);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
