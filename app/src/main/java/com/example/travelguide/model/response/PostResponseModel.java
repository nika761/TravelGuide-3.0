package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResponseModel implements Cloneable {

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
        private List<Post_locations> post_locations;
        @Expose
        @SerializedName("tagged_users")
        private List<Tagged_users> tagged_users;
        @Expose
        @SerializedName("hashtags")
        private List<Hashtags> hashtags;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("post_title")
        private String post_title;
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

        public List<Post_locations> getPost_locations() {
            return post_locations;
        }

        public void setPost_locations(List<Post_locations> post_locations) {
            this.post_locations = post_locations;
        }

        public List<Tagged_users> getTagged_users() {
            return tagged_users;
        }

        public void setTagged_users(List<Tagged_users> tagged_users) {
            this.tagged_users = tagged_users;
        }

        public List<Hashtags> getHashtags() {
            return hashtags;
        }

        public void setHashtags(List<Hashtags> hashtags) {
            this.hashtags = hashtags;
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

        public int getStory_id() {
            return story_id;
        }

        public void setStory_id(int story_id) {
            this.story_id = story_id;
        }
    }

    public static class Post_locations {
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

    public static class Tagged_users {
        @Expose
        @SerializedName("lastname")
        private String lastname;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("user_id")
        private int user_id;

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

    public static class Hashtags {
        @Expose
        @SerializedName("hashtag")
        private String hashtag;
        @Expose
        @SerializedName("id")
        private int id;

        public String getHashtag() {
            return hashtag;
        }

        public void setHashtag(String hashtag) {
            this.hashtag = hashtag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
