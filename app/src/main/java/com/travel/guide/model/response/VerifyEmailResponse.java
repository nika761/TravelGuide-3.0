package com.travel.guide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyEmailResponse {

    @Expose
    @SerializedName("access_token")
    private String access_token;
    @Expose
    @SerializedName("user")
    private LoginResponse.User user;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("status")
    private int status;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public LoginResponse.User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
