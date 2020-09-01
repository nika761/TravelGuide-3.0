package com.example.travelguide.model.request;

public class SignUpRequest {

    private String name;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private String password_confirmation;
    private String date_of_birth;
    private String phone_index;
    private String phone_num;
    private String language_id;

    public SignUpRequest() {
    }

    public SignUpRequest(String name, String lastname, String nickname, String email, String password, String password_confirmation, String date_of_birth, String phone_index, String phone_num, String language_id) {
        this.name = name;
        this.lastname = lastname;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.date_of_birth = date_of_birth;
        this.phone_index = phone_index;
        this.phone_num = phone_num;
        this.language_id = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone_index() {
        return phone_index;
    }

    public void setPhone_index(String phone_index) {
        this.phone_index = phone_index;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }
}
