package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddCommentReplyResponse {

    @Expose
    @SerializedName("comment_replies")
    private List<CommentResponse.Comment_reply> comment_replies;

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("commen_replies_count")
    private int commen_replies_count;

    public List<CommentResponse.Comment_reply> getComment_replies() {
        return comment_replies;
    }

    public int getStatus() {
        return status;
    }

    public int getCommen_replies_count() {
        return commen_replies_count;
    }
}
