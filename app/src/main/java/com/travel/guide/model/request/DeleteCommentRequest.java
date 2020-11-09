package com.travel.guide.model.request;

public class DeleteCommentRequest {

    private int post_id;

    private int story_id;
    private int comment_id;

    public DeleteCommentRequest(int post_id, int story_id, int comment_id) {
        this.post_id = post_id;
        this.story_id = story_id;
        this.comment_id = comment_id;
    }

}
