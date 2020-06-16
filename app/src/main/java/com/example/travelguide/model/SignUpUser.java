package com.example.travelguide.model;

public class SignUpUser {
    private String name;
    private String lastName;
    private String nickName;
    private String email;
    private String password;
    private String passwordConfirm;

    public SignUpUser(String name, String lastName, String nickName, String email, String password, String passwordConfirm) {
        this.name = name;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String toString() {
        return "SignUpUser{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
