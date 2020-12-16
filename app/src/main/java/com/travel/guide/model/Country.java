package com.travel.guide.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country implements Parcelable {

    @Expose
    @SerializedName("selected")
    private boolean selected;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("phone_index")
    private String phone_index;
    @Expose
    @SerializedName("id")
    private int id;

    protected Country(Parcel in) {
        selected = in.readByte() != 0;
        name = in.readString();
        phone_index = in.readString();
        id = in.readInt();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public boolean getSelected() {
        return selected;
    }

    public String getName() {
        return name;
    }

    public String getPhone_index() {
        return phone_index;
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
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(name);
        dest.writeString(phone_index);
        dest.writeInt(id);
    }
}
