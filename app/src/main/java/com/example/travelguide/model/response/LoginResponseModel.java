package com.example.travelguide.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class LoginResponseModel {

    @Expose
    @SerializedName("access_token")
    private String access_token;
    @Expose
    @SerializedName("user")
    private User user;

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

    public static class User implements Serializable {
        @Expose
        @SerializedName("user_lang")
        private String user_lang;
        @Expose
        @SerializedName("date_of_birth")
        private String date_of_birth;
        @Expose
        @SerializedName("phone_num")
        private String phone_num;
        @Expose
        @SerializedName("nickname")
        private String nickname;
        @Expose
        @SerializedName("updated_at")
        private String updated_at;
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("admin")
        private int admin;
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

        public String getPhone_num() {
            return phone_num;
        }

        public void setPhone_num(String phone_num) {
            this.phone_num = phone_num;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public int getAdmin() {
            return admin;
        }

        public void setAdmin(int admin) {
            this.admin = admin;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;
            User user = (User) o;
            return getAdmin() == user.getAdmin() &&
                    getId() == user.getId() &&
                    Objects.equals(getUser_lang(), user.getUser_lang()) &&
                    Objects.equals(getDate_of_birth(), user.getDate_of_birth()) &&
                    Objects.equals(getPhone_num(), user.getPhone_num()) &&
                    Objects.equals(getNickname(), user.getNickname()) &&
                    Objects.equals(getUpdated_at(), user.getUpdated_at()) &&
                    Objects.equals(getCreated_at(), user.getCreated_at()) &&
                    Objects.equals(getEmail(), user.getEmail()) &&
                    Objects.equals(getLastname(), user.getLastname()) &&
                    Objects.equals(getName(), user.getName());
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

}
