package travelguideapp.ge.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeCommentReplyResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("reply")
    private Reply reply;
    @Expose
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public Reply getReply() {
        return reply;
    }

    public int getStatus() {
        return status;
    }

    public static class Reply {
        @Expose
        @SerializedName("reply_likes")
        private int reply_likes;
        @Expose
        @SerializedName("reply_id")
        private String reply_id;

        public int getReply_likes() {
            return reply_likes;
        }

        public String getReply_id() {
            return reply_id;
        }
    }
}
