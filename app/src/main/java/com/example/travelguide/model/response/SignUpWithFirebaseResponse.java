package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpWithFirebaseResponse {

    @Expose
    @SerializedName("access_token")
    private String access_token;
    @Expose
    @SerializedName("user")
    private User user;
    @Expose
    @SerializedName("status")
    private int status;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class User {
        @Expose
        @SerializedName("id")
        private int id;
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("updated_at")
        private String updated_at;
        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;
        @Expose
        @SerializedName("user_lang")
        private String user_lang;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getUser_lang() {
            return user_lang;
        }

        public void setUser_lang(String user_lang) {
            this.user_lang = user_lang;
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
