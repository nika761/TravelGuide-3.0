package com.travelguide.travelguide.model.request;

public class ChangePasswordRequest {
    private String password;
    private String password_confirmation;

    public ChangePasswordRequest(String password, String password_confirmation) {
        this.password = password;
        this.password_confirmation = password_confirmation;
    }
}
