package com.travel.guide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FollowingResponse {

    @Expose
    @SerializedName("followings")
    private List<Followings> followings;
    @Expose
    @SerializedName("status")
    private int status;

    public List<Followings> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Followings> followings) {
        this.followings = followings;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Followings {
        @Expose
        @SerializedName("nickname")
        private String nickname;
        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("user_id")
        private int user_id;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
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
}
