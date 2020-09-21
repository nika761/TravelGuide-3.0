package com.example.travelguide.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public  class UserModel {

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

    public UserModel(String updated_at, String created_at, String profile_pic, String country, String date_of_birth, String user_lang, int admin, String email_verified_at, int gender, String phone_number, String email, String nickname, String lastname, String name, int id) {
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
        if (!(o instanceof UserModel)) return false;
        UserModel userModel = (UserModel) o;
        return getAdmin() == userModel.getAdmin() &&
                getGender() == userModel.getGender() &&
                getId() == userModel.getId() &&
                Objects.equals(getUpdated_at(), userModel.getUpdated_at()) &&
                Objects.equals(getCreated_at(), userModel.getCreated_at()) &&
                Objects.equals(getProfile_pic(), userModel.getProfile_pic()) &&
                Objects.equals(getCountry(), userModel.getCountry()) &&
                Objects.equals(getDate_of_birth(), userModel.getDate_of_birth()) &&
                Objects.equals(getUser_lang(), userModel.getUser_lang()) &&
                Objects.equals(getEmail_verified_at(), userModel.getEmail_verified_at()) &&
                Objects.equals(getPhone_number(), userModel.getPhone_number()) &&
                Objects.equals(getEmail(), userModel.getEmail()) &&
                Objects.equals(getNickname(), userModel.getNickname()) &&
                Objects.equals(getLastname(), userModel.getLastname()) &&
                Objects.equals(getName(), userModel.getName());
    }

}
