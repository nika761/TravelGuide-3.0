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
}
