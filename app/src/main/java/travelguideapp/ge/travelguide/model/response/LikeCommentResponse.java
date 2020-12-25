package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeCommentResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("comment")
    private Comment comment;
    @Expose
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Comment {
        @Expose
        @SerializedName("comment_likes")
        private int comment_likes;
        @Expose
        @SerializedName("comment_id")
        private String comment_id;

        public int getComment_likes() {
            return comment_likes;
        }

        public void setComment_likes(int comment_likes) {
            this.comment_likes = comment_likes;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }
    }
}
