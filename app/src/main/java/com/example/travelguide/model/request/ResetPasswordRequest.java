package com.example.travelguide.model.request;

public class ResetPasswordRequest {
    private String email;
    private String token;
    private String password;
    private String password_confirmation;
    private int language_id;

    public ResetPasswordRequest(String email, String token, String password, String password_confirmation, int language_id) {
        this.email = email;
        this.token = token;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.language_id = language_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }
}
