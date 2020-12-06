package com.travel.guide.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class ChangePasswordRequest implements Parcelable {
    private String old_password;
    private String new_password;
    private String confirm_password;

    public ChangePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
        this.old_password = currentPassword;
        this.new_password = newPassword;
        this.confirm_password = confirmPassword;
    }

    private ChangePasswordRequest(Parcel in) {
        old_password = in.readString();
        new_password = in.readString();
        confirm_password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(old_password);
        dest.writeString(new_password);
        dest.writeString(confirm_password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChangePasswordRequest> CREATOR = new Creator<ChangePasswordRequest>() {
        @Override
        public ChangePasswordRequest createFromParcel(Parcel in) {
            return new ChangePasswordRequest(in);
        }

        @Override
        public ChangePasswordRequest[] newArray(int size) {
            return new ChangePasswordRequest[size];
        }
    };

    public String getCurrentPassword() {
        return old_password;
    }

    public String getNewPassword() {
        return new_password;
    }

    public String getConfirmPassword() {
        return confirm_password;
    }
}
