package com.travel.guide.model.request;

public class UpdateProfileRequest {

    private String name;
    private String lastname;
    private String nickname;
    private String date_of_birth;
    private String phone_index;
    private String phone_num;
    private String biography;
    private String country;
    private String email;
    private String gender;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setPhone_index(String phone_index) {
        this.phone_index = phone_index;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
