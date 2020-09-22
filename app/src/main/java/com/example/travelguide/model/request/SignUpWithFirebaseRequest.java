package com.example.travelguide.model.request;

public class SignUpWithFirebaseRequest {
    private String token;
    private String nickname;
    private String date_of_birth;
    private int language_id;
    private int platform_id;

    public SignUpWithFirebaseRequest(String token, String nickname, String date_of_birth, int language_id, int platform_id) {
        this.token = token;
        this.nickname = nickname;
        this.date_of_birth = date_of_birth;
        this.language_id = language_id;
        this.platform_id = platform_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public int getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(int platform_id) {
        this.platform_id = platform_id;
    }
}
