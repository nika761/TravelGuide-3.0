package com.travelguide.travelguide.model.request;

public class LikeCommentReplyRequest {

    private int story_id;
    private int post_id;
    private int comment_id;
    private int comment_reply_id;

    public LikeCommentReplyRequest(int story_id, int post_id, int comment_id, int comment_reply_id) {
        this.story_id = story_id;
        this.post_id = post_id;
        this.comment_id = comment_id;
        this.comment_reply_id = comment_reply_id;
    }

}
