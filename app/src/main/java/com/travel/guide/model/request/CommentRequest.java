package com.travel.guide.model.request;

public class CommentRequest {

    private int story_id;

    private int post_id;

    private int from_comment_id;

    public CommentRequest(int story_id, int post_id, int from_comment_id) {
        this.story_id = story_id;
        this.post_id = post_id;
        this.from_comment_id = from_comment_id;
    }

}
