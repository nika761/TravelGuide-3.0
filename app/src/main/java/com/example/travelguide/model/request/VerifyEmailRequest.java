package com.example.travelguide.model.request;

public class VerifyEmailRequest {

    private String user_id;
    private String signature;

    public VerifyEmailRequest(String user_id, String signature) {
        this.user_id = user_id;
        this.signature = signature;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
