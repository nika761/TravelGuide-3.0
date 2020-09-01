package com.example.travelguide.model.request;

public class ForgotPasswordRequest {
    private String email;
    private int language_id;

    public ForgotPasswordRequest(String email, int language_id) {
        this.email = email;
        this.language_id = language_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }
}
