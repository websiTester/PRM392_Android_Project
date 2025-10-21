package com.example.prm392_android_project.retrofit.Client;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//WARNING: day la cua Quan de test dung goi hay dong vao
public class RetrofitClient2 {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:5049";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }




}
