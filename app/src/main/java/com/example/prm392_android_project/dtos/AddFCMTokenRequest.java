package com.example.prm392_android_project.dtos;

public class AddFCMTokenRequest {
    private String token;
    private String userId;
    private String deviceName;


    public AddFCMTokenRequest(String token, String userId, String deviceName) {
        this.token = token;
        this.userId = userId;
        this.deviceName = deviceName;
    }

    public AddFCMTokenRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
