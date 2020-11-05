package com.travel.guide.model.request;

public class LikeCommentRequest {

    private int story_id;
    private int post_id;
    private int comment_id;

    public LikeCommentRequest(int story_id, int post_id, int comment_id) {
        this.story_id = story_id;
        this.post_id = post_id;
        this.comment_id = comment_id;
    }
}
