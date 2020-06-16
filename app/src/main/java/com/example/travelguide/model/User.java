package com.example.travelguide.model;

import java.util.Objects;

public class User {
    private String name;
    private String lastName;
    private String url;
    private String id;
    private String email;
    private String loginType;
    private String loginStatus;

    public User(String name, String lastName, String url, String id, String email, String loginType) {
        this.name = name;
        this.lastName = lastName;
        this.url = url;
        this.id = id;
        this.email = email;
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoginStatus() {
        return loginStatus;
    }
    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getUrl(), user.getUrl()) &&
                Objects.equals(getId(), user.getId()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getLoginType(), user.getLoginType());
    }
}
