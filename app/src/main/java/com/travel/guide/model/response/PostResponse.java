package com.travel.guide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostResponse {

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

    public static class Posts implements Serializable {
        @Expose
        @SerializedName("post_stories")
        private List<Post_stories> post_stories;
        @Expose
        @SerializedName("post_reactions")
        private int post_reactions;
        @Expose
        @SerializedName("i_favor_post")
        private boolean i_favor_post;
        @Expose
        @SerializedName("i_follow_post_owner")
        private boolean i_follow_post_owner;
        @Expose
        @SerializedName("post_locations")
        private List<Post_locations> post_locations;
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
        @SerializedName("past_time")
        private String past_time;
        @Expose
        @SerializedName("music_url")
        private String music_url;
        @Expose
        @SerializedName("music_text")
        private String music_text;
        @Expose
        @SerializedName("post_share_url")
        private String post_share_url;
        @Expose
        @SerializedName("post_view")
        private int post_view;
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("cover")
        private String cover;
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

        public int getPost_reactions() {
            return post_reactions;
        }

        public void setPost_reactions(int post_reactions) {
            this.post_reactions = post_reactions;
        }

        public boolean getI_favor_post() {
            return i_favor_post;
        }

        public void setI_favor_post(boolean i_favor_post) {
            this.i_favor_post = i_favor_post;
        }

        public boolean getI_follow_post_owner() {
            return i_follow_post_owner;
        }

        public void setI_follow_post_owner(boolean i_follow_post_owner) {
            this.i_follow_post_owner = i_follow_post_owner;
        }

        public List<Post_locations> getPost_locations() {
            return post_locations;
        }

        public void setPost_locations(List<Post_locations> post_locations) {
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

        public String getPast_time() {
            return past_time;
        }

        public void setPast_time(String past_time) {
            this.past_time = past_time;
        }

        public String getMusic_url() {
            return music_url;
        }

        public void setMusic_url(String music_url) {
            this.music_url = music_url;
        }

        public String getMusic_text() {
            return music_text;
        }

        public void setMusic_text(String music_text) {
            this.music_text = music_text;
        }

        public String getPost_share_url() {
            return post_share_url;
        }

        public void setPost_share_url(String post_share_url) {
            this.post_share_url = post_share_url;
        }

        public int getPost_view() {
            return post_view;
        }

        public void setPost_view(int post_view) {
            this.post_view = post_view;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
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

    public static class Post_stories implements Serializable {
        @Expose
        @SerializedName("story_like_by_me")
        private boolean story_like_by_me;
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

        public boolean getStory_like_by_me() {
            return story_like_by_me;
        }

        public void setStory_like_by_me(boolean story_like_by_me) {
            this.story_like_by_me = story_like_by_me;
        }

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

    public static class Post_locations implements Serializable {
        @Expose
        @SerializedName("lng")
        private String lng;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("address")
        private String address;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
