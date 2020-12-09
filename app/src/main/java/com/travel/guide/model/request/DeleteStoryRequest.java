package com.travel.guide.model.request;

public class DeleteStoryRequest {
    private int post_id;

    public DeleteStoryRequest(int post_id) {
        this.post_id = post_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
