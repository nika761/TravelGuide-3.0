package com.example.travelguide.model.request;

public class CommentRequest {
    private int story_id;
    private int post_id;

    public CommentRequest(int story_id, int post_id) {
        this.story_id = story_id;
        this.post_id = post_id;
    }
}
