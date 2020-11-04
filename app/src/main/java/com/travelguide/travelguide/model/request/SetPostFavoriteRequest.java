package com.travelguide.travelguide.model.request;

public class SetPostFavoriteRequest {
    private int post_id;

    public SetPostFavoriteRequest(int post_id) {
        this.post_id = post_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
