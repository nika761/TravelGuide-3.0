package com.example.travelguide.model.request;

public class AddCommentRequest {

    private int story_id;
    private int post_id;
    private String comment;

    public AddCommentRequest(int story_id, int post_id, String comment) {
        this.story_id = story_id;
        this.post_id = post_id;
        this.comment = comment;
    }

    public int getStory_id() {
        return story_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public String getComment() {
        return comment;
    }

}
