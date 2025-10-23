package com.example.prm392_android_project.retrofit.Client;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:5049";
    private static volatile Retrofit instance;
    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    // Đặt mức log thành BODY để thấy Request Headers, Body và Response
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                    // 2. Tạo OkHttpClient và thêm Interceptor
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(logging) // Thêm logging interceptor
                            .build();
                    instance = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }
        return instance;
    }
}
