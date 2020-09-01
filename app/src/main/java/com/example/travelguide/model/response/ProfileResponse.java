package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @Expose
    @SerializedName("userinfo")
    private Userinfo userinfo;
    @Expose
    @SerializedName("status")
    private int status;

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Userinfo {
        @Expose
        @SerializedName("reactions")
        private int reactions;
        @Expose
        @SerializedName("follower")
        private int follower;
        @Expose
        @SerializedName("following")
        private int following;

        @Expose
        @SerializedName("follow")
        private int follow;

        @Expose
        @SerializedName("share_profile")
        private String share_profile;
        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;
        @Expose
        @SerializedName("biography")
        private String biography;
        @Expose
        @SerializedName("phone_number")
        private String phone_number;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("gender")
        private int gender;
        @Expose
        @SerializedName("date_of_birth")
        private String date_of_birth;
        @Expose
        @SerializedName("nickname")
        private String nickname;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("lastname")
        private String lastname;
        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("id")
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getReactions() {
            return reactions;
        }

        public void setReactions(int reactions) {
            this.reactions = reactions;
        }

        public int getFollower() {
            return follower;
        }

        public void setFollower(int follower) {
            this.follower = follower;
        }

        public int getFollowing() {
            return following;
        }

        public void setFollowing(int following) {
            this.following = following;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public String getShare_profile() {
            return share_profile;
        }

        public void setShare_profile(String share_profile) {
            this.share_profile = share_profile;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getDate_of_birth() {
            return date_of_birth;
        }

        public void setDate_of_birth(String date_of_birth) {
            this.date_of_birth = date_of_birth;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

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
    }
}
