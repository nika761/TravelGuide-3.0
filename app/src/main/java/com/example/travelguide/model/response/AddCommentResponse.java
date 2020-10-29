package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddCommentResponse {

    @Expose
    @SerializedName("post_story_comments")
    private List<CommentResponse.Post_story_comments> post_story_comments;

    @Expose
    @SerializedName("comment")
    private String comment;
    @Expose

    @SerializedName("status")
    private int status;

    public List<CommentResponse.Post_story_comments> getPost_story_comments() {
        return post_story_comments;
    }

    public void setPost_story_comments(List<CommentResponse.Post_story_comments> post_story_comments) {
        this.post_story_comments = post_story_comments;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
