package com.example.prm392_android_project.retrofit.Client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRetrofitClient {
    private static final String BASE_URL2 = "http://10.0.2.2:5049";
    private static volatile Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl(BASE_URL2)
                            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return instance;
    }
}
