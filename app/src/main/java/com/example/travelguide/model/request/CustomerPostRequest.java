package com.example.travelguide.model.request;

public class CustomerPostRequest {
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public CustomerPostRequest(int user_id) {
        this.user_id = user_id;
    }
}
