package com.example.travelguide.model.request;

public class PostRequestModel {
    int user_id;

    public PostRequestModel(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
