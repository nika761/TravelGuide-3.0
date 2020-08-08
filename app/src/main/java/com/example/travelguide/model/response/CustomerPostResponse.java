package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerPostResponse {
    @Expose
    @SerializedName("posts")
    private List<Posts> posts;
    @Expose
    @SerializedName("status")
    private int status;

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Posts {
        @Expose
        @SerializedName("post_stories")
        private List<Post_stories> post_stories;
        @Expose
        @SerializedName("post_locations")
        private List<String> post_locations;
        @Expose
        @SerializedName("hashtags")
        private List<String> hashtags;
        @Expose
        @SerializedName("post_shares")
        private int post_shares;
        @Expose
        @SerializedName("post_favorites")
        private int post_favorites;
        @Expose
        @SerializedName("post_go_activity")
        private int post_go_activity;
        @Expose
        @SerializedName("text_for_page")
        private String text_for_page;
        @Expose
        @SerializedName("post_share_url")
        private String post_share_url;
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("music_id")
        private int music_id;
        @Expose
        @SerializedName("go")
        private String go;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("post_title")
        private String post_title;
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
        @SerializedName("post_id")
        private int post_id;

        public List<Post_stories> getPost_stories() {
            return post_stories;
        }

        public void setPost_stories(List<Post_stories> post_stories) {
            this.post_stories = post_stories;
        }

        public List<String> getPost_locations() {
            return post_locations;
        }

        public void setPost_locations(List<String> post_locations) {
            this.post_locations = post_locations;
        }

        public List<String> getHashtags() {
            return hashtags;
        }

        public void setHashtags(List<String> hashtags) {
            this.hashtags = hashtags;
        }

        public int getPost_shares() {
            return post_shares;
        }

        public void setPost_shares(int post_shares) {
            this.post_shares = post_shares;
        }

        public int getPost_favorites() {
            return post_favorites;
        }

        public void setPost_favorites(int post_favorites) {
            this.post_favorites = post_favorites;
        }

        public int getPost_go_activity() {
            return post_go_activity;
        }

        public void setPost_go_activity(int post_go_activity) {
            this.post_go_activity = post_go_activity;
        }

        public String getText_for_page() {
            return text_for_page;
        }

        public void setText_for_page(String text_for_page) {
            this.text_for_page = text_for_page;
        }

        public String getPost_share_url() {
            return post_share_url;
        }

        public void setPost_share_url(String post_share_url) {
            this.post_share_url = post_share_url;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getMusic_id() {
            return music_id;
        }

        public void setMusic_id(int music_id) {
            this.music_id = music_id;
        }

        public String getGo() {
            return go;
        }

        public void setGo(String go) {
            this.go = go;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
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

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }
    }

    public static class Post_stories {
        @Expose
        @SerializedName("second")
        private int second;
        @Expose
        @SerializedName("url")
        private String url;
        @Expose
        @SerializedName("story_comments")
        private int story_comments;
        @Expose
        @SerializedName("story_likes")
        private int story_likes;
        @Expose
        @SerializedName("story_id")
        private int story_id;

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getStory_comments() {
            return story_comments;
        }

        public void setStory_comments(int story_comments) {
            this.story_comments = story_comments;
        }

        public int getStory_likes() {
            return story_likes;
        }

        public void setStory_likes(int story_likes) {
            this.story_likes = story_likes;
        }

        public int getStory_id() {
            return story_id;
        }

        public void setStory_id(int story_id) {
            this.story_id = story_id;
        }
    }
}