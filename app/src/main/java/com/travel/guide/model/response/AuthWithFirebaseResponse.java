package com.travel.guide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AuthWithFirebaseResponse {

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("user")
    private LoginResponse.User user;

    @Expose
    @SerializedName("access_token")
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LoginResponse.User getUser() {
        return user;
    }

    public void setUser(LoginResponse.User user) {
        this.user = user;
    }

    public static class User {
        @Expose
        @SerializedName("updated_at")
        private String updated_at;
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;
        @Expose
        @SerializedName("country")
        private String country;
        @Expose
        @SerializedName("date_of_birth")
        private String date_of_birth;
        @Expose
        @SerializedName("user_lang")
        private String user_lang;
        @Expose
        @SerializedName("admin")
        private int admin;
        @Expose
        @SerializedName("email_verified_at")
        private String email_verified_at;
        @Expose
        @SerializedName("gender")
        private int gender;
        @Expose
        @SerializedName("phone_number")
        private String phone_number;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("nickname")
        private String nickname;
        @Expose
        @SerializedName("lastname")
        private String lastname;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public User(String updated_at, String created_at, String profile_pic, String country, String date_of_birth, String user_lang, int admin, String email_verified_at, int gender, String phone_number, String email, String nickname, String lastname, String name, int id) {
            this.updated_at = updated_at;
            this.created_at = created_at;
            this.profile_pic = profile_pic;
            this.country = country;
            this.date_of_birth = date_of_birth;
            this.user_lang = user_lang;
            this.admin = admin;
            this.email_verified_at = email_verified_at;
            this.gender = gender;
            this.phone_number = phone_number;
            this.email = email;
            this.nickname = nickname;
            this.lastname = lastname;
            this.name = name;
            this.id = id;
        }

        public User() {
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDate_of_birth() {
            return date_of_birth;
        }

        public void setDate_of_birth(String date_of_birth) {
            this.date_of_birth = date_of_birth;
        }

        public String getUser_lang() {
            return user_lang;
        }

        public void setUser_lang(String user_lang) {
            this.user_lang = user_lang;
        }

        public int getAdmin() {
            return admin;
        }

        public void setAdmin(int admin) {
            this.admin = admin;
        }

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public void setEmail_verified_at(String email_verified_at) {
            this.email_verified_at = email_verified_at;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LoginResponse.User)) return false;
            LoginResponse.User user = (LoginResponse.User) o;
            return getAdmin() == user.getAdmin() &&
                    getGender() == user.getGender() &&
                    getId() == user.getId() &&
                    Objects.equals(getUpdated_at(), user.getUpdated_at()) &&
                    Objects.equals(getCreated_at(), user.getCreated_at()) &&
                    Objects.equals(getProfile_pic(), user.getProfile_pic()) &&
                    Objects.equals(getCountry(), user.getCountry()) &&
                    Objects.equals(getDate_of_birth(), user.getDate_of_birth()) &&
                    Objects.equals(getUser_lang(), user.getUser_lang()) &&
                    Objects.equals(getEmail_verified_at(), user.getEmail_verified_at()) &&
                    Objects.equals(getPhone_number(), user.getPhone_number()) &&
                    Objects.equals(getEmail(), user.getEmail()) &&
                    Objects.equals(getNickname(), user.getNickname()) &&
                    Objects.equals(getLastname(), user.getLastname()) &&
                    Objects.equals(getName(), user.getName());
        }

    }

}
