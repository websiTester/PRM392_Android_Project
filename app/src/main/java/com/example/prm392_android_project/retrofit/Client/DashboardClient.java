package com.example.prm392_android_project.retrofit.Client;

import com.example.prm392_android_project.retrofit.API.DashboardAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardClient {
    private static final String BASE_URL = "http://10.0.2.2:5049";
    private static DashboardAPI dashboardAPI;

    public static DashboardAPI getDashboardAPI() {
        if (dashboardAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            dashboardAPI = retrofit.create(DashboardAPI.class);
        }
        return dashboardAPI;
    }
}
