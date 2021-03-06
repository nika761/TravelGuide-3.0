package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteCommentResponse {

    @Expose
    @SerializedName("post_story_comments")
    private List<CommentResponse.Post_story_comments> post_story_comments;
    @Expose
    @SerializedName("count")
    private int count;
    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("message")
    private String message;

    public List<CommentResponse.Post_story_comments> getPost_story_comments() {
        return post_story_comments;
    }

    public String getMessage() {
        return message;
    }

    public int getCount() {
        return count;
    }

    public int getStatus() {
        return status;
    }

}
