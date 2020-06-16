package com.example.travelguide.model;

import com.example.travelguide.model.SignUpUser;

public class AuthResponseModel {
    SignUpUser user;
    String access_token;

    public AuthResponseModel(SignUpUser user, String access_token) {
        this.user = user;
        this.access_token = access_token;
    }

    public SignUpUser getUser() {
        return user;
    }

    public void setUser(SignUpUser user) {
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "AuthResponseModel{" +
                "user=" + user +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
