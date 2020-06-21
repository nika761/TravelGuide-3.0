package com.example.travelguide.model.request;

public class AuthRequestModel {

    private String name;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private String password_confirmation;
    private String date_of_birth;
    private String phone_num;

    public AuthRequestModel() {
    }

    public AuthRequestModel(String name, String lastname, String nickname, String email, String password, String password_confirmation,String birthDate,String phoneNumber) {
        this.name = name;
        this.lastname = lastname;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.date_of_birth = birthDate;
        this.phone_num = phoneNumber;
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

    public String getBirthDate() {
        return date_of_birth;
    }

    public void setBirthDate(String birthDate) {
        this.date_of_birth = birthDate;
    }

    public String getPhoneNumber() {
        return phone_num;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_num = phoneNumber;
    }
}
