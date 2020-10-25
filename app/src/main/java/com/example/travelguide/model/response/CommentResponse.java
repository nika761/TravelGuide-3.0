package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentResponse {

    @Expose
    @SerializedName("post_story_comments")
    private List<Post_story_comments> post_story_comments;
    @Expose
    @SerializedName("count")
    private int count;
    @Expose
    @SerializedName("status")
    private int status;

    public List<Post_story_comments> getPost_story_comments() {
        return post_story_comments;
    }

    public void setPost_story_comments(List<Post_story_comments> post_story_comments) {
        this.post_story_comments = post_story_comments;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Post_story_comments {
        @Expose
        @SerializedName("comment_reply")
        private List<Comment_reply> comment_reply;
        @Expose
        @SerializedName("comment_likes")
        private int comment_likes;
        @Expose
        @SerializedName("comment_time")
        private String comment_time;
        @Expose
        @SerializedName("i_can_edit_comment")
        private boolean i_can_edit_comment;
        @Expose
        @SerializedName("i_can_reply_comment")
        private boolean i_can_reply_comment;
        @Expose
        @SerializedName("comment_liked_by_me")
        private boolean comment_liked_by_me;
        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;
        @Expose
        @SerializedName("nickname")
        private String nickname;
        @Expose
        @SerializedName("user_id")
        private int user_id;
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("text")
        private String text;
        @Expose
        @SerializedName("story_id")
        private int story_id;
        @Expose
        @SerializedName("comment_id")
        private int comment_id;

        public List<Comment_reply> getComment_reply() {
            return comment_reply;
        }

        public void setComment_reply(List<Comment_reply> comment_reply) {
            this.comment_reply = comment_reply;
        }

        public int getComment_likes() {
            return comment_likes;
        }

        public void setComment_likes(int comment_likes) {
            this.comment_likes = comment_likes;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public boolean getI_can_edit_comment() {
            return i_can_edit_comment;
        }

        public void setI_can_edit_comment(boolean i_can_edit_comment) {
            this.i_can_edit_comment = i_can_edit_comment;
        }

        public boolean isI_can_reply_comment() {
            return i_can_reply_comment;
        }

        public void setI_can_reply_comment(boolean i_can_reply_comment) {
            this.i_can_reply_comment = i_can_reply_comment;
        }

        public boolean isComment_liked_by_me() {
            return comment_liked_by_me;
        }

        public void setComment_liked_by_me(boolean comment_liked_by_me) {
            this.comment_liked_by_me = comment_liked_by_me;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getStory_id() {
            return story_id;
        }

        public void setStory_id(int story_id) {
            this.story_id = story_id;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }
    }

    public static class Comment_reply {
        @Expose
        @SerializedName("reply_time")
        private String reply_time;
        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;
        @Expose
        @SerializedName("nickname")
        private String nickname;
        @Expose
        @SerializedName("user_id")
        private int user_id;
        @Expose
        @SerializedName("text")
        private String text;
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("comment_reply_id")
        private int comment_reply_id;

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getComment_reply_id() {
            return comment_reply_id;
        }

        public void setComment_reply_id(int comment_reply_id) {
            this.comment_reply_id = comment_reply_id;
        }
    }
}
