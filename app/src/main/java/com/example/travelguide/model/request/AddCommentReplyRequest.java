package com.example.travelguide.model.request;

public class AddCommentReplyRequest {

    private int story_id;
    private int post_id;
    private int comment_id;
    private String comment;

    public AddCommentReplyRequest(int story_id, int post_id, int comment_id, String comment) {
        this.story_id = story_id;
        this.post_id = post_id;
        this.comment_id = comment_id;
        this.comment = comment;
    }
}
