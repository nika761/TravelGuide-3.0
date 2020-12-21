package com.travel.guide.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.travel.guide.model.Country;

import java.util.ArrayList;
import java.util.List;

public class ProfileResponse implements Parcelable {

    @Expose
    @SerializedName("userinfo")
    private Userinfo userinfo;

    @Expose
    @SerializedName("status")
    private int status;

    protected ProfileResponse(Parcel in) {
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileResponse> CREATOR = new Creator<ProfileResponse>() {
        @Override
        public ProfileResponse createFromParcel(Parcel in) {
            return new ProfileResponse(in);
        }

        @Override
        public ProfileResponse[] newArray(int size) {
            return new ProfileResponse[size];
        }
    };

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public int getStatus() {
        return status;
    }

    public static class Userinfo implements Parcelable {

        @Expose
        @SerializedName("reactions")
        private int reactions;
        @Expose
        @SerializedName("show_favourite_but")
        private int show_favourite_but;
        @Expose
        @SerializedName("follower")
        private int follower;
        @Expose
        @SerializedName("following")
        private int following;
        @Expose
        @SerializedName("follow")
        private int follow;
        @Expose
        @SerializedName("share_profile")
        private String share_profile;
        @Expose
        @SerializedName("countries")
        private List<Country> countries;
        @Expose
        @SerializedName("profile_pic")
        private String profile_pic;
        @Expose
        @SerializedName("biography")
        private String biography;
        @Expose
        @SerializedName("phone_number")
        private String phone_number;

        @Expose
        @SerializedName("phone_index")
        private String phone_index;

        @Expose
        @SerializedName("city")
        private String city;

        @Expose
        @SerializedName("country")
        private String country;

        @Expose
        @SerializedName("gender")
        private int gender;

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
        @Expose
        @SerializedName("id")
        private int id;

        Userinfo(Parcel in) {
            reactions = in.readInt();
            show_favourite_but = in.readInt();
            follower = in.readInt();
            following = in.readInt();
            follow = in.readInt();
            share_profile = in.readString();
            profile_pic = in.readString();
            biography = in.readString();
            phone_number = in.readString();
            phone_index = in.readString();
            city = in.readString();
            country = in.readString();
            gender = in.readInt();
            date_of_birth = in.readString();
            nickname = in.readString();
            email = in.readString();
            lastname = in.readString();
            name = in.readString();
            id = in.readInt();
            if (in.readByte() == 0x01) {
                countries = new ArrayList<>();
                in.readList(countries, Country.class.getClassLoader());
            } else {
                countries = null;
            }
        }

        public static final Creator<Userinfo> CREATOR = new Creator<Userinfo>() {
            @Override
            public Userinfo createFromParcel(Parcel in) {
                return new Userinfo(in);
            }

            @Override
            public Userinfo[] newArray(int size) {
                return new Userinfo[size];
            }
        };

        public int getReactions() {
            return reactions;
        }

        public int getShow_favourite_but() {
            return show_favourite_but;
        }

        public int getFollower() {
            return follower;
        }

        public int getFollowing() {
            return following;
        }

        public int getFollow() {
            return follow;
        }

        public String getShare_profile() {
            return share_profile;
        }

        public List<Country> getCountries() {
            return countries;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public String getBiography() {
            return biography;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public String getPhone_index() {
            return phone_index;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public int getGender() {
            return gender;
        }

        public String getDate_of_birth() {
            return date_of_birth;
        }

        public String getNickname() {
            return nickname;
        }

        public String getEmail() {
            return email;
        }

        public String getLastname() {
            return lastname;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(reactions);
            dest.writeInt(show_favourite_but);
            dest.writeInt(follower);
            dest.writeInt(following);
            dest.writeInt(follow);
            dest.writeString(share_profile);
            dest.writeString(profile_pic);
            dest.writeString(biography);
            dest.writeString(phone_number);
            dest.writeString(phone_index);
            dest.writeString(city);
            dest.writeString(country);
            dest.writeInt(gender);
            dest.writeString(date_of_birth);
            dest.writeString(nickname);
            dest.writeString(email);
            dest.writeString(lastname);
            dest.writeString(name);
            dest.writeInt(id);
            if (countries == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeList(countries);
            }
        }
    }

}
