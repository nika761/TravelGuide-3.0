package com.travelguide.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoreReplyResponse {

    @Expose
    @SerializedName("comment_replies")
    private List<CommentResponse.Comment_reply> comment_replies;
    @Expose
    @SerializedName("status")
    private int status;

    public List<CommentResponse.Comment_reply> getComment_replies() {
        return comment_replies;
    }

    public int getStatus() {
        return status;
    }

}
