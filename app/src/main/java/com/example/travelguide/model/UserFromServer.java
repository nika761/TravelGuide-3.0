package com.example.travelguide.model;
import java.io.Serializable;
import java.util.Objects;

public class UserFromServer implements Serializable {
    private String user_lang;
    private String date_of_birth;
    private String phone_num;
    private String nickname;
    private String updated_at;
    private String created_at;
    private int admin;
    private String email;
    private String lastname;
    private String name;
    private int id;
    private String country;
    private String email_verified_at;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFromServer)) return false;
        UserFromServer that = (UserFromServer) o;
        return getAdmin() == that.getAdmin() &&
                getId() == that.getId() &&
                Objects.equals(getUser_lang(), that.getUser_lang()) &&
                Objects.equals(getDate_of_birth(), that.getDate_of_birth()) &&
                Objects.equals(getPhone_num(), that.getPhone_num()) &&
                Objects.equals(getNickname(), that.getNickname()) &&
                Objects.equals(getUpdated_at(), that.getUpdated_at()) &&
                Objects.equals(getCreated_at(), that.getCreated_at()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getLastname(), that.getLastname()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getEmail_verified_at(), that.getEmail_verified_at());
    }

    @Override
    public int hashCode() {
        return 0;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }
}
