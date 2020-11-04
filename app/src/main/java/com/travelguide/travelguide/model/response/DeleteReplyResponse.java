package com.travelguide.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteReplyResponse {

    @Expose
    @SerializedName("comment_replies")
    private List<CommentResponse.Comment_reply> comment_replies;

    @Expose
    @SerializedName("comment_replies_count")
    private int comment_replies_count;

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("message")
    private String message;

    public List<CommentResponse.Comment_reply> getComment_reply() {
        return comment_replies;
    }

    public String getMessage() {
        return message;
    }

    public int getCount() {
        return comment_replies_count;
    }

    public int getStatus() {
        return status;
    }


}
