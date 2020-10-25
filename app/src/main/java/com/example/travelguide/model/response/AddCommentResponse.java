package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCommentResponse {

    @Expose
    @SerializedName("comment")
    private String comment;
    @Expose
    @SerializedName("status")
    private int status;

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
